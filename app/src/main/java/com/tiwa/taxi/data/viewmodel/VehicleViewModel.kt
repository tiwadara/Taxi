package com.tiwa.taxi.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tiwa.taxi.data.dao.VehicleDatabase
import com.tiwa.taxi.data.model.Poi
import com.tiwa.taxi.data.repository.VehicleRepository

class VehicleViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: VehicleRepository
    private var poisList: LiveData<List<Poi>>
    private var selectedPoi : LiveData<Poi>

    init {
        val poiDao = VehicleDatabase.getDatabase(application, viewModelScope).poiDao()
        repository = VehicleRepository(poiDao, application, viewModelScope)
        poisList = repository.poisList
        selectedPoi = repository.selectedPoi

    }

    fun getPoi(): LiveData<List<Poi>> {
        return poisList
    }

    fun getIsLoading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getSelectedPoi(): LiveData<Poi> {
        return selectedPoi

    }
    fun setSelected (poi: Poi) {
        repository.setSelected(poi)
    }


}