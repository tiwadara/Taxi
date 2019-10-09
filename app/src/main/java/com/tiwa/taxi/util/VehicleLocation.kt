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
            val maxResultReturned = 1
            addresses = geocoder.getFromLocation(location.latitude, location.longitude,
                maxResultReturned
            )
        } catch (e: Exception) {
        }
        val fullAddress = 0
        val addressLine = 0
        return addresses?.get(fullAddress)?.getAddressLine(addressLine)
    }

}