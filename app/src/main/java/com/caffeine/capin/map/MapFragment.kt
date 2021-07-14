package com.caffeine.capin.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.category.SelectCategoryActivity
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.util.AutoClearedValue
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        mapView = binding.mapview
        mapView.getMapAsync(this)

//        viewModel.switchToCapinMap()
        setCafeInformation()
        setToolbar()
        archiveCafeToMyMap()
        updateCafeDeatail()
        changeMap()

    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.uiSettings.isLocationButtonEnabled = false
        binding.zoomcontrolview.map = naverMap
        binding.locationButton.map = naverMap

        setMarker()
        checkPermissions()
        getTagResult()
    }

    private fun getTagResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<List<CafeInformationEntity>>(
            "tagResult"
        )
            ?.observe(viewLifecycleOwner) {
                removeActiveMarkers()

                viewModel.changeCapinMapLocations(it)
                Log.e("hello", "$it")
            }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            moveToMyLocation()
        } else {
            requestLocationPermission.launch(LOCATION_PERMISSION)
        }
    }

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                moveToMyLocation()
            }
        }

    private fun moveToMyLocation() {
        locationSource = FusedLocationSource(this, PERMISSION_FUSED_LOCATION)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.locationSource = locationSource
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setMenuButton {
                findNavController().navigate(R.id.action_mapFragment_to_mapProfileFragment)
            }
            setTagSearchButton {
                findNavController().navigate(R.id.action_mapFragment_to_tagFilterFragment)
            }
        }
    }

    private fun setCafeInformation() {
        viewModel.capinMapCafeLocation.observe(viewLifecycleOwner) {
            setMarkersInsideCamera()
            setCameraChangeListener()
        }
    }

    private fun changeMap() {
        binding.radiogroupMap.apply {
            check(binding.radiobuttonCapinMap.id)
            setOnCheckedChangeListener { _, checkedId ->
                removeActiveMarkers()
                when (checkedId) {
                    binding.radiobuttonMyMap.id -> {
                        viewModel.switchToMyMap()
                    }
                    binding.radiobuttonCapinMap.id -> {
                        viewModel.switchToCapinMap()
                    }
                }
            }
        }
    }

    private fun setCameraChangeListener() {
        naverMap.addOnCameraChangeListener { reason, animated ->
            setMarkersInsideCamera()
        }
    }

    private fun setMarkersInsideCamera() {
        val eastBound = naverMap.coveringBounds.eastLongitude
        val westBound = naverMap.coveringBounds.westLongitude
        val northBound = naverMap.coveringBounds.northLatitude
        val southBound = naverMap.coveringBounds.southLatitude

        viewModel.removeAllCafeCurrentCamera()
        viewModel.capinMapCafeLocation.value?.forEach { location ->
            if (location.longitude in westBound..eastBound) {
                if (location.latitude in southBound..northBound) {
                    viewModel.addCafeInsideCurrentCamera(location, false)
                }
            }
        }

        if (viewModel.cafeCurrentChecked.value != null) {
            viewModel.addCafeInsideCurrentCamera(viewModel.cafeCurrentChecked.value!!, true)
        }
    }

    private fun setMarker() {
        viewModel.cafeInsideCurrentCamera.observe(viewLifecycleOwner) { cafeList ->
            removeActiveMarkers()
            initSelectMarker()
        }
    }

    private fun initSelectMarker() {
        viewModel.cafeInsideCurrentCamera.value?.forEach { cafe ->
            val marker = Marker()
            marker.position = LatLng(cafe.key.latitude, cafe.key.longitude)
            viewModel.addExposedMarker(marker)

            if (cafe.value) {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_pin_active_cate_10)
                marker.map = naverMap
            } else {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_pin_inactive_default)
                marker.map = naverMap
            }

            //Todo: OnClickListener 메서드 분리시키기
            marker.setOnClickListener(object : Overlay.OnClickListener {
                override fun onClick(overlay: Overlay): Boolean {
                    if (overlay is Marker) {
                        viewModel.changeCafeCurrentChecked(cafe.key)
                        viewModel.addCafeInsideCurrentCamera(cafe.key, true)
                        viewModel.getSelectedCafeDetailInfo()
                        return true
                    }
                    return false
                }
            })
        }
    }

    private fun updateCafeDeatail() {
        viewModel.selectedCafe.observe(viewLifecycleOwner) { cafeDetail ->
            binding.apply {
                textviewAddress.text = cafeDetail.address
                textviewCafeName.text = cafeDetail.name
                textviewCafeRating.text = cafeDetail.average.toString()
                textviewCafeTag.text = cafeDetail.tags[0].name
            }
        }
    }

    private fun archiveCafeToMyMap() {
        binding.buttonSaveCafe.setOnClickListener {
            val intent = Intent(requireContext(), SelectCategoryActivity::class.java)
            intent.putExtra(CAFE_NAME, binding.textviewCafeName.text)
            startActivity(intent)
        }
    }

    private fun removeActiveMarkers() {
        viewModel.exposedMarker.value?.forEach { marker ->
            marker.map = null
        }
        viewModel.clearExposedMarker()
    }

    companion object {
        private val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val PERMISSION_FUSED_LOCATION = 1000
        private const val CAFE_NAME = "CafeName"
    }
}