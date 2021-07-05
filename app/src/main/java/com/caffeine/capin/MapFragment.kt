package com.caffeine.capin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.util.AutoClearedValue
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MapFragment: Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
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
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        changeMap()
    }

    private fun changeMap() {
        binding.radiogroupMap.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId) {
                    binding.radiobuttonMyMap.id -> {
                        getCurrentZoomBound(myMapPositions)
                    }
                    binding.radiobuttonCapinMap.id -> {
                        getCurrentZoomBound(capinMapPositions)
                    }
                }
            }
        })
    }

    private fun getCurrentZoomBound(markersPosition: ArrayList<LatLng>) {
        naverMap.addOnCameraChangeListener { reason, animated ->
            freeActiveMarkers()
            getCameraBounds(markersPosition)
        }
    }

    private fun getCameraBounds(markersPosition: ArrayList<LatLng>) {
        val eastBound = naverMap.coveringBounds.eastLongitude
        val westBound = naverMap.coveringBounds.westLongitude
        val northBound = naverMap.coveringBounds.northLatitude
        val southBound = naverMap.coveringBounds.southLatitude

        markersPosition.forEach { markerPosition ->
            if (markerPosition.longitude in westBound..eastBound) {
                if(markerPosition.latitude in southBound..northBound) {
                    setMarker(markerPosition)
                }
            }
        }
    }

    private fun setMarker(position: LatLng) {
        val marker = Marker()
        marker.position = position
        marker.icon = OverlayImage.fromResource(R.drawable.marker_capinmap_unselected)
        marker.map = naverMap
        activeMarkers.add(marker)
    }

    private fun freeActiveMarkers() {
        activeMarkers.forEach{ marker ->
            marker.map = null
        }
    }

    companion object {
        private val capinMapPositions: ArrayList<LatLng> = arrayListOf<LatLng>(
            LatLng(37.580221,
                126.923442),
            LatLng(37.582996109622876,
                126.91380431146156),
            LatLng(37.579256039764246,
                126.92016389612074),
            LatLng(
                37.556561236976535,
                126.92786145335195
            )
        )

        private val myMapPositions: ArrayList<LatLng> = arrayListOf(
            LatLng(37.5598780330203, 126.92397328447223),
            LatLng(37.5628956716561, 126.92529371146098),
            LatLng(37.56111453790112, 126.92340389420406),
            LatLng(37.56216407332653, 126.92306471146108)
        )
    }
}