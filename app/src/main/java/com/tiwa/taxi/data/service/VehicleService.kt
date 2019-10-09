package com.tiwa.taxi.data.service

import com.tiwa.taxi.data.constant.Constants.BASE_URL
import com.tiwa.taxi.data.model.Vehicle
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


internal interface VehicleService {

    companion object Factory {
        fun getInstance(): VehicleService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(VehicleService::class.java)
        }
    }

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET(".")
    fun getVehicles(
        @Query("p1Lat") latitude1: String,
        @Query("p1Lon") longitude1: String,
        @Query("p2Lat") latitude2: String,
        @Query("p2Lon") longitude2: String
    ): Call<Vehicle>

}