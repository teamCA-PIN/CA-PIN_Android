package com.caffeine.capin.map

import android.Manifest
import android.app.Activity
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinToastMessage.createCapinToast
import com.caffeine.capin.databinding.FragmentMapBinding
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
    private val selectedMarker = Marker()
    private lateinit var locationSource: FusedLocationSource
    private var count = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        mapView = binding.mapview
        mapView.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setCafeInformation()
        setToolbar()
        showCafeCardView()
        showToast()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.uiSettings.isLocationButtonEnabled = false
        binding.zoomcontrolview.map = naverMap
        binding.locationButton.map = naverMap

        changeMap()
        setMarker()
        checkPermissions()
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
        viewModel.cafeInformation.observe(viewLifecycleOwner) {
            setMarkersInsideCamera()
            setCameraChangeListener()
        }
    }

    private fun changeMap() {
        binding.radiogroupMap.check(binding.radiobuttonCapinMap.id)
        binding.radiogroupMap.setOnCheckedChangeListener { _, checkedId ->
            freeActiveMarkers()
            selectedMarker.map = null

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

    private fun setCameraChangeListener() {
        naverMap.addOnCameraChangeListener { reason, animated ->
            setMarkersInsideCamera()
            if (viewModel.selectedMarker.value != null) {
                setMarker()
            }
        }
    }

    private fun setMarkersInsideCamera() {
        val eastBound = naverMap.coveringBounds.eastLongitude
        val westBound = naverMap.coveringBounds.westLongitude
        val northBound = naverMap.coveringBounds.northLatitude
        val southBound = naverMap.coveringBounds.southLatitude
        viewModel.removeAllCafeCurrentCamera()
        viewModel.cafeInformation.value?.forEach { location ->
            if (location.longtitude in westBound..eastBound) {
                if (location.latitude in southBound..northBound) {
                    viewModel.addCafeInsideCurrentCamera(location)
                }
            }
        }
    }

    private fun showCafeCardView() {
        viewModel.selectedCafeInfo.observe(viewLifecycleOwner) { selectedCafe ->
            if (selectedCafe != null) {
                binding.cardviewCafeSelected.visibility = View.VISIBLE
            } else {
                binding.cardviewCafeSelected.visibility = View.GONE
            }
        }
    }

    private fun setMarker() {
        viewModel.cafeSelectedStatus.observe(viewLifecycleOwner) { cafeList ->
            freeActiveMarkers()
            cafeList.forEach { cafe ->
                val marker = Marker()
                marker.position = LatLng(cafe.key.latitude, cafe.key.longtitude)
                viewModel.addExposedMarker(marker)

                if (cafe.value) {
                    marker.icon = OverlayImage.fromResource(R.drawable.ic_pin_active_cate_10)
                    marker.map = naverMap
                } else {
                    marker.icon = OverlayImage.fromResource(R.drawable.ic_pin_inactive_cate_10)
                    marker.map = naverMap
                }

                //Todo: OnClickListener 메서드 분리시키기
                marker.setOnClickListener(object : Overlay.OnClickListener {
                    override fun onClick(overlay: Overlay): Boolean {
                        if (overlay is Marker) {
                            if (marker.position.latitude == cafe.key.latitude && marker.position.longitude == cafe.key.longtitude) {
                                viewModel.changeSelectedCafeInfo(cafe.key)
                                viewModel.changeMapValue(cafe.key, true)
                            } else {
                                viewModel.changeMapValue(cafe.key, false)
                            }
                            return true
                        }
                        return false
                    }
                })
            }
        }
    }

    private fun showToast() {
        binding.buttonSaveCafe.setOnClickListener {
            createCapinToast(requireContext(), "카테고리에 저장되었습니다.",325)?.show()
        }
    }

    private fun freeActiveMarkers() {
        viewModel.exposedMarker.value?.forEach { marker ->
            marker.map = null
        }
        viewModel.clearExposedMarker()
    }

    companion object {
        private val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val PERMISSION_FUSED_LOCATION = 1000
    }
}