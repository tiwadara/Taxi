package com.tiwa.taxi.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LAT1
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LAT2
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LONG1
import com.tiwa.taxi.data.constant.Constants.HAMBURG_LONG2
import com.tiwa.taxi.data.dao.PoiDao
import com.tiwa.taxi.data.dao.VehicleDatabase
import com.tiwa.taxi.data.model.Poi
import com.tiwa.taxi.data.model.Vehicle
import com.tiwa.taxi.data.service.VehicleService
import com.tiwa.taxi.util.VehicleLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VehicleRepository(
    private var poiDao: PoiDao,
    var application: Application,
    var scope: CoroutineScope
) {
    var isLoading = MutableLiveData<Boolean>()
    var poisList: LiveData<List<Poi>>
    var selectedPoi: LiveData<Poi>
    private val vehicleService = VehicleService.Factory

    init {
        poiDao = VehicleDatabase.getDatabase(application.applicationContext, scope).poiDao()
        poisList = poiDao.getAllPois()
        selectedPoi = poiDao.getSelectedPoi()
        getPois()
    }

    fun setSelected(poi: Poi) {
        scope.launch(Dispatchers.IO) {
            poi.isSelected = true
            poiDao.updatePoi(poi)
        }
    }

    private fun getPois() {

        scope.launch {
            val dataExists = poiDao.hasPoi()
            if (!dataExists) {
                isLoading.value = true
                vehicleService.getInstance().getVehicles(HAMBURG_LAT1, HAMBURG_LONG1, HAMBURG_LAT2, HAMBURG_LONG2)
                    .enqueue(object : Callback<Vehicle> {
                        override fun onFailure(call: Call<Vehicle>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                            saveNewPois(response)
                        }

                    })
            }
        }

    }

    private fun saveNewPois(response: Response<Vehicle>) {

        scope.launch(Dispatchers.IO) {

            val newPoiList: MutableList<Poi> = mutableListOf()
            for (poi in response.body()?.poiList!!) {
                poi.address = VehicleLocation.getLocationAddress(application, poi.coordinate)
                newPoiList.add(poi)
            }
            poiDao.updatePoiList(newPoiList)

        }.invokeOnCompletion {
            scope.launch {
                isLoading.value = false
            }
        }

    }

}
