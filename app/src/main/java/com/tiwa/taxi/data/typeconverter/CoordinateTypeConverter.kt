package com.tiwa.taxi.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.tiwa.taxi.data.model.Coordinate

class CoordinateTypeConverter {
    @TypeConverter
    fun toCoordinate (string: String?): Coordinate? {
        return if (string == null) null
        else Gson().fromJson(string, Coordinate::class.java)
    }

    @TypeConverter
    fun fromCoordinate (coordinate: Coordinate?): String? {
        return (if (coordinate == null) null else
            Gson().toJson(coordinate))
    }
}