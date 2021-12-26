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
import com.bumptech.glide.Glide
import com.caffeine.capin.R
import com.caffeine.capin.category.model.CategoryType
import com.caffeine.capin.category.ui.SelectCategoryActivity
import com.caffeine.capin.customview.CustomToastTextView
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
        binding.cardviewCafeSelected.visibility = View.GONE

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
        binding.run {
            imageviewSetting.setOnClickListener {
                findNavController().navigate(R.id.action_mapFragment_to_mapProfileFragment)
            }
            imageviewFilter.setOnClickListener {
                findNavController().navigate(R.id.action_mapFragment_to_tagFilterFragment)
            }
            imageviewMypage.setOnClickListener {
                val intent = Intent(requireContext(), MyPageActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
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
            when (viewModel.isMyMap.value) {
                true -> check(binding.radiobuttonMyMap.id)
                false -> check(binding.radiobuttonCapinMap.id)
            }
            setOnCheckedChangeListener { _, checkedId ->
                binding.cardviewCafeSelected.run {
                    if (visibility == View.VISIBLE) {
                        applyVisibilityAnimation(isUpward = false, reveal = false, durationTime = 500)
                    }
                }
                removeActiveMarkers()
                checkMapSort()
            }
        }
    }

    private fun checkMapSort() {
        binding.radiobuttonMyMap.setOnClickListener {
            CustomToastTextView(requireContext(), null, "마이맵", null, 0.8, binding.constraintlayoutMap)
        }
        binding.radiobuttonCapinMap.setOnClickListener {
            CustomToastTextView(requireContext(), null, "카핀맵", null, 0.8, binding.constraintlayoutMap)
        }
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
        naverMap.addOnCameraIdleListener { setMarkersInsideCamera() }
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
            with(marker) {
                position = LatLng(cafe.key.latitude, cafe.key.longitude)
                viewModel.addExposedMarker(this)
                icon = OverlayImage.fromResource(if (cafe.value) CategoryType.findActiveType(cafe.key.markType) else CategoryType.findInactiveType(cafe.key.markType))
                map = naverMap
            }
            clickMarker(marker, cafe)
        }
    }

    private fun clickMarker(marker: Marker, cafe: Map.Entry<CafeInformationEntity, Boolean>?) {
        marker.onClickListener = object: Overlay.OnClickListener{
            override fun onClick(overlay: Overlay): Boolean {
                if (overlay is Marker) {
                    binding.cardviewCafeSelected.run{
                        if(visibility == View.GONE) {
                            visibility = View.VISIBLE
                            applyVisibilityAnimation(isUpward = true, reveal = true, durationTime = 500)
                        }
                    }
                    cafe?.let {
                        viewModel.changeCafeCurrentChecked(it.key)
                        viewModel.addCafeInsideCurrentCamera(it.key, true)
                        viewModel.getSelectedCafeDetailInfo()
                    }
                    return true
                }
                return false
            }
        }
    }

    private fun updateCafeDetail() {
        viewModel.selectedCafe.observe(viewLifecycleOwner) { cafeDetail ->
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
            viewModel.selectedCafe.observe(viewLifecycleOwner) {
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
            val jsonCafeInfo = gson.toJson(viewModel.selectedCafe.value?.data)
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
        viewModel.selectedCafe.observe(viewLifecycleOwner) {
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
        private val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val PERMISSION_FUSED_LOCATION = 1000
        private const val SELECTED_CAFE_INFO = "selected_cafe_info"
    }
}