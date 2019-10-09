package com.tiwa.taxi.ui.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.tiwa.taxi.R
import com.tiwa.taxi.data.constant.Constants
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LAT1
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LAT2
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LONG1
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LONG2
import com.tiwa.taxi.data.model.Poi
import com.tiwa.taxi.data.viewmodel.VehicleViewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var vehicleViewModel: VehicleViewModel
    private lateinit var map: GoogleMap
    private var zoomIntoOne: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        zoomIntoOne = MapFragmentArgs.fromBundle(arguments!!).zoomIntoOne
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true

        observerViewModels()
    }

    private fun placeMarkersOnMap(poisList: List<Poi>) {
        map.clear()
        for (poi in poisList) {
            val location = LatLng(poi.coordinate.latitude, poi.coordinate.longitude)
            val markerOptions = MarkerOptions().position(location)
            when {
                poi.fleetType == Constants.POOLING -> {

                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(rotateBitmap(R.mipmap.ic_pool, poi)))
                }

                poi.fleetType == Constants.TAXI -> {

                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(rotateBitmap(R.mipmap.ic_taxi, poi)))

                }

            }

            map.addMarker(markerOptions)
        }

    }

    private fun zoomIntoHamburg() {
        val pos = LatLng(HAMBURG_LAT1.toDouble(), HAMBURG_LONG1.toDouble())
        val pos1 = LatLng(HAMBURG_LAT2.toDouble(), HAMBURG_LONG2.toDouble())
        val builder = LatLngBounds.Builder()
        builder.include(pos)
        builder.include(pos1)
        val bounds = builder.build()

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()

        val cu = newLatLngBounds(bounds, width, height, padding)
        map.animateCamera(cu)
    }

    private fun zoomIntoVehicle(poi: Poi) {
        val location = LatLng(poi.coordinate.latitude, poi.coordinate.longitude)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f), 2000, null)
    }

    private fun rotateBitmap(bitmap: Int, poi: Poi): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(180 + poi.heading.toFloat())
        val source = BitmapFactory.decodeResource(this.resources, bitmap)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    private fun observerViewModels() {

        if (zoomIntoOne) {
            observePoiList()
            vehicleViewModel.getSelectedPoi().observe(this, Observer<Poi> { poi: Poi? ->

                if (poi != null) {
                    zoomIntoVehicle(poi)
                }
            })
        } else {
            zoomIntoHamburg()
            observePoiList()
        }


    }

    private fun observePoiList() {
        vehicleViewModel.getPoi().observe(this,
            Observer<List<Poi>> { poisList ->
                if (poisList != null) {
                    placeMarkersOnMap(poisList)
                    vehicleViewModel.getPoi()
                }
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vehicleViewModel = ViewModelProviders.of(this).get(VehicleViewModel::class.java)

    }

}
