package com.tiwa.taxi.data.viewmodel

import android.app.Application
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class VehicleViewModelTest {

    @Mock
    lateinit var application: Application
    lateinit var vehicleViewModel: VehicleViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vehicleViewModel = VehicleViewModel(application)
    }
    @Test
    fun getPoi() {
    }

    @Test
    fun getIsLoading() {
        val applicationMock = Mockito.mock(Application::class.java)
        val vehicleViewModel = VehicleViewModel(applicationMock)
        assertEquals(true, vehicleViewModel.getIsLoading())
        assertEquals(false, vehicleViewModel.getIsLoading())

    }

    @Test
    fun getSelectedPoi() {
    }

    @Test
    fun setSelected() {
    }
}