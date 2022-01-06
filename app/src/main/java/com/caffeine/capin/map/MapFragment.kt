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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.category.model.CategoryType
import com.caffeine.capin.category.ui.SelectCategoryActivity
import com.caffeine.capin.customview.CustomToastBuilder
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.detail.CafeDetailsActivity
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.mypage.ui.MyPageActivity
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
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
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
    private val viewModel by activityViewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

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
        updateCafeDetail()
        checkIsSavedCafe()
    }

    override fun onResume() {
        super.onResume()
        checkMapSort()
        if (viewModel.cafeCurrentChecked.value == null) {
            binding.cardviewCafeSelected.visibility = View.GONE
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.uiSettings.isLocationButtonEnabled = false
        binding.zoomcontrolview.map = naverMap
        binding.locationButton.map = naverMap

        checkPermissions()
        getTagResult()
        changeMap()
        setMarker()
        setCafeDetailTags()
        checkMapSort()
        setMarkerVisibility()
        setCameraChangeListener()
    }

    private fun getTagResult() {
        viewModel.checkedTagList.value?.let {
            if (viewModel.checkedTagList.value!!.all { it == null }) {
                viewModel.initializeFilterTag()
            }
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            moveToMyLocation()
        } else {
            requestLocationPermission.launch(LOCATION_PERMISSION)
        }
    }

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) { moveToMyLocation() }
        }

    private fun moveToMyLocation() {
        locationSource = FusedLocationSource(this, PERMISSION_FUSED_LOCATION)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    private fun setToolbar() {
        binding.run {
            imageviewSetting.setOnClickListener {
                initSelectedCafe()
                findNavController().navigate(R.id.action_mapFragment_to_mapProfileFragment)
            }
            imageviewFilter.setOnClickListener {
                initSelectedCafe()
                findNavController().navigate(R.id.action_mapFragment_to_tagFilterFragment)
            }
            imageviewMypage.setOnClickListener {
                initSelectedCafe()
                val intent = Intent(requireContext(), MyPageActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
            }
        }
    }

    private fun initSelectedCafe() {
        viewModel.clearCheckedCafe()
        viewModel.deleteCafeDetail()
        binding.cardviewCafeSelected.applyVisibilityAnimation(isUpward = false, reveal = false, durationTime = 500)
    }

    private fun setCameraChangeListener() {
        naverMap.addOnCameraIdleListener {
            setMarkersInsideCamera()
        }
    }

    private fun setCafeInformation() {
        viewModel.cafeLocations.observe(viewLifecycleOwner) {
            setMarkersInsideCamera()
        }
    }

    private fun setMarkersInsideCamera() {
        val cafeList = mutableListOf<CafeInformationEntity>()
        val eastBound = naverMap.coveringBounds.eastLongitude
        val westBound = naverMap.coveringBounds.westLongitude
        val northBound = naverMap.coveringBounds.northLatitude
        val southBound = naverMap.coveringBounds.southLatitude

        viewModel.run {
            cafeLocations.value?.forEach { location ->
                if (location.longitude in westBound..eastBound && location.latitude in southBound..northBound) {
                    cafeList.add(location)
                }
            }
            viewModel.changeCafeInsideCurrentCamera(cafeList)
        }
    }

    private fun setMarker() {
        viewModel.cafeLocations.observe(viewLifecycleOwner) { cafes ->
            val markers = mutableListOf<Marker>()
            cafes.forEach {
                val marker = Marker()
                with(marker) {
                    position = LatLng(it.latitude, it.longitude)
                    icon = if (viewModel.cafeCurrentChecked.value?.latitude == it.latitude &&
                        viewModel.cafeCurrentChecked.value?.longitude == it.longitude) {
                        OverlayImage.fromResource(CategoryType.findActiveType(it.markType))
                    } else {
                        OverlayImage.fromResource(CategoryType.findInactiveType(it.markType))
                    }
                    isVisible = false
                    map = naverMap
                    markers.add(this)
                    clickMarker(this)
                }
            }
            viewModel.changeExposedMarkers(markers)
        }
    }

    private fun clickMarker(cafeMarker: Marker) {
        cafeMarker.onClickListener = Overlay.OnClickListener {
            binding.cardviewCafeSelected.run{
                if(visibility == View.GONE) {
                    visibility = View.VISIBLE
                    applyVisibilityAnimation(isUpward = true, reveal = true, durationTime = 500)
                }
            }
            val clickedCafe = viewModel.cafeInsideCurrentCamera.value?.find {
                it.longitude == cafeMarker.position.longitude && it.latitude == cafeMarker.position.latitude
            }
            clickedCafe?.let {
                cafeMarker.icon = OverlayImage.fromResource(CategoryType.findActiveType(it.markType))
            }

            viewModel.exposedMarker.value?.forEach { before ->
                viewModel.cafeCurrentChecked.value?.let {
                    if (before.position.latitude == it.latitude &&
                        before.position.longitude == it.longitude) {
                        before.icon = OverlayImage.fromResource(CategoryType.findInactiveType(it.markType))
                    }
                }
            }
            viewModel.changeCafeCurrentChecked(clickedCafe)
            false
        }
    }

    private fun setMarkerVisibility() {
        viewModel.cafeInsideCurrentCamera.observe(viewLifecycleOwner) {
            checkIsVisibleMarker()
        }
        viewModel.exposedMarker.observe(viewLifecycleOwner) {
            checkIsVisibleMarker()
        }
    }

    private fun checkIsVisibleMarker() {
        viewModel.cafeInsideCurrentCamera.value?.let {
            val cafePositions = it.map { LatLng(it.latitude, it.longitude) }
            viewModel.exposedMarker.value?.forEach {
                it.isVisible = cafePositions.contains(it.position)
            }
        }
    }



    private fun changeMap() {
        binding.radiogroupMap.apply {
            when (viewModel.isMyMap.value) {
                true -> check(binding.radiobuttonMyMap.id)
                false -> check(binding.radiobuttonCapinMap.id)
            }
            setOnCheckedChangeListener { _, checkedId ->
                viewModel.removeAllCafeCurrentCamera()
                binding.cardviewCafeSelected.run {
                    if (visibility == View.VISIBLE) {
                        applyVisibilityAnimation(isUpward = false, reveal = false, durationTime = 500)
                    }
                }
                removeActiveMarkers()
                viewModel.clearCheckedCafe()
            }
        }
    }

    private fun checkMapSort() {
        binding.radiobuttonMyMap.setOnClickListener {
            viewModel.changeIsMyMap(true)
            viewModel.getMyMapPins()
            CustomToastBuilder(requireContext(), "마이맵", binding.constraintlayoutMap)
                .setYLocation(0.8)
                .build()

        }
        binding.radiobuttonCapinMap.setOnClickListener {
            viewModel.changeIsMyMap(false)
            viewModel.getCapinMapPins()
            CustomToastBuilder(requireContext(), "카핀맵", binding.constraintlayoutMap)
                .setYLocation(0.8)
                .build()
        }
    }

    private fun updateCafeDetail() {
        viewModel.cafeCurrentChecked.observe(viewLifecycleOwner) {
            viewModel.getSelectedCafeDetailInfo()
        }

        viewModel.selectedCafeDetail.observe(viewLifecycleOwner) { cafeDetail ->
            binding.cardviewCafeSelected.run {
                isEnabled = (cafeDetail.status == UiState.Status.SUCCESS)
                setOnClickListener {
                    Intent(requireActivity(), CafeDetailsActivity::class.java)
                        .putExtra(CafeDetailsActivity.KEY_CAFE_ID, cafeDetail.data?._id)
                        .also { startActivity(it) }
                }
            }
        }
    }

    private fun setCafeDetailTags() {
        binding.recyclerviewTags.run {
            adapter = CafeTagListAdapter()
            addItemDecoration(VerticalItemDecoration(3.toPx()))
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            viewModel.selectedCafeDetail.observe(viewLifecycleOwner) {
                (binding.recyclerviewTags.adapter as CafeTagListAdapter).submitList(it.data?.tags?.map { it.name })
                when(it.status) {
                    UiState.Status.LOADING -> applySkeletonUI(true)
                    UiState.Status.SUCCESS -> applySkeletonUI(false)
                    UiState.Status.ERROR -> applySkeletonUI(false)
                }
            }
        }
    }

    private fun archiveCafeToMyMap() {
        binding.constraintlayoutPinSave.setOnClickListener {
            val gson = Gson()
            val jsonCafeInfo = gson.toJson(viewModel.selectedCafeDetail.value?.data)
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

    private fun applySkeletonUI(showShimmer: Boolean) {
        binding.shimmerlayout.run {
            showShimmer(showShimmer)
            visibility = if (showShimmer) View.VISIBLE else View.GONE
        }
    }

    private fun checkIsSavedCafe() {
        viewModel.selectedCafeDetail.observe(viewLifecycleOwner) {
            binding.run {
                checkboxPinIcon.isChecked = (it.data?.isSaved == true)
                checkboxPinText.isChecked = (it.data?.isSaved == true)
                constraintlayoutPinSave.background = if (it.data?.isSaved == true) {
                    ContextCompat.getDrawable(requireContext(), R.drawable.shape_blue_5dp)
                } else {
                    ContextCompat.getDrawable(requireContext(), R.drawable.shape_blue_stroke_5dp)
                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val PERMISSION_FUSED_LOCATION = 1000
        const val SELECTED_CAFE_INFO = "selected_cafe_info"
    }
}