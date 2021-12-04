package com.caffeine.capin.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.caffeine.capin.R
import com.caffeine.capin.category.model.CategoryType
import com.caffeine.capin.category.ui.SelectCategoryActivity
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.detail.CafeDetailsActivity
import com.caffeine.capin.util.AutoClearedValue
import com.google.gson.Gson
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
    private val viewModel by activityViewModels<MapViewModel>()
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


        setCafeInformation()
        setToolbar()
        archiveCafeToMyMap()
        updateCafeDeatail()
        showCafeDetail()

    }

    override fun onResume() {
        super.onResume()
        checkMapSort()
        binding.cardviewCafeSelected.visibility = View.GONE

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
        changeMap()
    }

    private fun getTagResult() {
        if (viewModel.checkedTagList.value != null) {
            if (viewModel.checkedTagList.value!!.all { it == null }) {
                binding.toolbar.changeTagSearchBackground(R.drawable.ic_btn_tag_inactive)
                viewModel.initializeFilterTag()
            } else {
                binding.toolbar.changeTagSearchBackground(R.drawable.ic_btn_tag_btn_tag_active)
            }
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
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

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

    private fun showCafeDetail() {
        viewModel.selectedCafe.observe(viewLifecycleOwner) {
            binding.cardviewCafeSelected.visibility = View.VISIBLE
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
            when (viewModel.isMyMap.value) {
                true -> {
                    check(binding.radiobuttonMyMap.id)
                }
                false -> {
                    check(binding.radiobuttonCapinMap.id)
                }
            }

            setOnCheckedChangeListener { _, checkedId ->
                binding.cardviewCafeSelected.visibility = View.GONE
                removeActiveMarkers()
                checkMapSort()
            }
        }
    }

    private fun checkMapSort() {
        when (binding.radiogroupMap.checkedRadioButtonId) {
            binding.radiobuttonMyMap.id -> {
                viewModel.changeIsMyMap(true)
                viewModel.getMyMapPins()
            }
            binding.radiobuttonCapinMap.id -> {
                viewModel.changeIsMyMap(false)
                viewModel.getCapinMapPins()
            }
        }
    }

    private fun setCameraChangeListener() {
        naverMap.addOnCameraIdleListener(object : NaverMap.OnCameraIdleListener {
            override fun onCameraIdle() {
                setMarkersInsideCamera()
            }
        })
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
                marker.icon =
                    OverlayImage.fromResource(CategoryType.findActiveType(cafe.key.markType))
                marker.map = naverMap
            } else {
                marker.icon =
                    OverlayImage.fromResource(CategoryType.findInactiveType(cafe.key.markType))
                marker.map = naverMap
            }

            //Fixme: OnClickListener 메서드 분리시키기
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
                if (!cafeDetail.tags.isNullOrEmpty()) {
                    textviewCafeTag.visibility = View.VISIBLE
                    textviewCafeTag.text = cafeDetail.tags[0].name
                } else {
                    textviewCafeTag.visibility = View.GONE
                }
                if(!cafeDetail.img.isNullOrEmpty()) {
                    Glide.with(requireContext()).load(cafeDetail.img).into(imageviewCafe)
                } else {
                    Glide.with(requireContext()).load(R.drawable.ic_component_86).into(binding.imageviewCafe)
                }
                cardviewCafeSelected.setOnClickListener {
                    Intent(activity, CafeDetailsActivity::class.java)
                        .putExtra(CafeDetailsActivity.KEY_CAFE_ID, cafeDetail._id)
                        .also { startActivity(it) }
                }
            }
        }
    }

    private fun archiveCafeToMyMap() {
        binding.buttonSaveCafe.setOnClickListener {
            val gson = Gson()
            val jsonCafeInfo = gson.toJson(viewModel.selectedCafe.value)
            val intent = Intent(requireContext(), SelectCategoryActivity::class.java)
            intent.putExtra(SELECTED_CAFE_INFO, jsonCafeInfo)
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
        private const val SELECTED_CAFE_INFO = "selected_cafe_info"
    }
}