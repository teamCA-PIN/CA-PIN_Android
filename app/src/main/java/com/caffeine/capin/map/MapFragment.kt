package com.caffeine.capin.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.util.AutoClearedValue
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var mapView: MapView
    private val selectedMarker = Marker()

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
        setToolbar()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        setCafeInformation()
        changeMap()
        fetchSelectedCafe()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setMenuButton {
                findNavController().navigate(R.id.action_mapFragment_to_mapProfileFragment)
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
            freeActiveMarkers()
            setMarkersInsideCamera()
            if (viewModel.selectedMarker.value != null) {
                selectLocation(viewModel.selectedMarker.value!!.position)
            }

        }
    }

    private fun setMarkersInsideCamera() {
        val eastBound = naverMap.coveringBounds.eastLongitude
        val westBound = naverMap.coveringBounds.westLongitude
        val northBound = naverMap.coveringBounds.northLatitude
        val southBound = naverMap.coveringBounds.southLatitude

        viewModel.cafeInformation.value?.forEach { location ->
            if (location.longtitude in westBound..eastBound) {
                if (location.latitude in southBound..northBound) {
                    setMarker(LatLng(location.latitude, location.longtitude))
                }
            }
        }
    }

    private fun setMarker(location: LatLng) {
        val marker = Marker()
        marker.position = location
        marker.icon = OverlayImage.fromResource(R.drawable.marker_capinmap_unselected)
        marker.map = naverMap
        viewModel.addExposedMarker(marker)

        viewModel.exposedMarker.value?.forEach { marker ->
            marker.setOnClickListener(object : Overlay.OnClickListener{
                override fun onClick(overlay: Overlay): Boolean {
                    if (overlay is Marker) {
                        viewModel.changeSelectedMarker(overlay)
                        return true
                    }
                    return false
                }
            })
        }
    }

    private fun fetchSelectedCafe() {
        viewModel.selectedMarker.observe(viewLifecycleOwner) { marker ->
            selectLocation(marker.position)
        }
    }

    //Todo: 마커 클릭시
    private fun selectLocation(location: LatLng) {
            selectedMarker.map = null
            selectedMarker.position = location
            selectedMarker.icon = OverlayImage.fromResource(R.drawable.ic_pin_selected)
            selectedMarker.map = naverMap
    }

    private fun freeActiveMarkers() {
        viewModel.exposedMarker.value?.forEach { marker ->
            marker.map = null
        }
        viewModel.clearExposedMarker()
    }
}