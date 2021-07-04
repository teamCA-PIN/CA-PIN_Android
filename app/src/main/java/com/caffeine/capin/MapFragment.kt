package com.caffeine.capin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.util.AutoClearedValue
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: Fragment(), OnMapReadyCallback {
    private var binding by AutoClearedValue<FragmentMapBinding>()
    private lateinit var naverMap: NaverMap
    private lateinit var mapView: MapView

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
        setMarker(37.580221,126.923442)
        setMarker(37.579174,126.923809)
    }

    private fun setMarker(lat: Double, long : Double) {
        val marker = Marker()
        Log.e("marker", "marker")
        marker.icon = OverlayImage.fromResource(R.drawable.marker_capinmap_unselected)
        marker.position = LatLng(lat, long)
        val cameraUpdate = CameraUpdate.scrollTo(marker.position).animate(CameraAnimation.Easing)
        marker.map = naverMap
        naverMap.moveCamera(cameraUpdate)
        naverMap.contentBounds
    }

    private fun getCurrentZoomBound() {
        naverMap.addOnCameraChangeListener(object: NaverMap.OnCameraChangeListener{
            override fun onCameraChange(reason: Int, animated: Boolean) {
                
            }
        })
    }
}