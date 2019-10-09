package com.tiwa.taxi.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.tiwa.taxi.data.model.Coordinate
import java.util.*


object VehicleLocation {

    fun getLocationAddress(context: Context?, location: Coordinate): String? {
        var addresses: List<Address>? = null
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (e: Exception) {
        }
        return addresses?.get(0)?.getAddressLine(0)
    }

}