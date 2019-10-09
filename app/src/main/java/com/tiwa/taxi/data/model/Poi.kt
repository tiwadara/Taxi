package com.tiwa.taxi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiwa.taxi.data.constant.Constants.POI_TABLE


@Entity(tableName = POI_TABLE)

data class Poi(
    val coordinate: Coordinate,
    var address: String?,
    var isSelected: Boolean = false,
    val fleetType: String,
    val heading: Double,
    @PrimaryKey
    val id: Int
)