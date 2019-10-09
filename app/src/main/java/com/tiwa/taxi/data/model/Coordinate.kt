package com.tiwa.taxi.data.model

import androidx.room.Entity
import com.tiwa.taxi.data.constant.Constants.COORDINATE_TABLE

@Entity(tableName = COORDINATE_TABLE)
data class Coordinate(
    val latitude: Double,
    val longitude: Double
)