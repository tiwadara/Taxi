package com.tiwa.taxi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiwa.taxi.data.constant.Constants.VEHICLE_TABLE

@Entity(tableName = VEHICLE_TABLE)
data class Vehicle (

    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val poiList: List<Poi>

)
