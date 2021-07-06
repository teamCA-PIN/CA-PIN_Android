package com.caffeine.capin.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.util.AutoClearedValue
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var mapView: MapView
    private var activeMarkers = ArrayList<Marker>()

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
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        setCafeInformation()
        changeMap()
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
        }
    }

    private fun setMarkersInsideCamera() {
        val eastBound = naverMap.coveringBounds.eastLongitude
        val westBound = naverMap.coveringBounds.westLongitude
        val northBound = naverMap.coveringBounds.northLatitude
        val southBound = naverMap.coveringBounds.southLatitude

        viewModel.cafeInformation.value?.forEach { location ->
            if (location.longtitude in westBound..eastBound) {
                if(location.latitude in southBound..northBound) {
                    setMarker(LatLng(location.latitude, location.longtitude))
                }
            }
            Log.d("active marker", "${activeMarkers}")
        }
    }

    private fun setMarker(location: LatLng) {
        val marker = Marker()
        marker.position = location
        marker.icon = OverlayImage.fromResource(R.drawable.marker_capinmap_unselected)
        marker.map = naverMap
        activeMarkers.add(marker)
    }

    private fun freeActiveMarkers() {
        activeMarkers.forEach{ marker ->
            marker.map = null
        }
    }
}