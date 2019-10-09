package com.tiwa.taxi.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tiwa.taxi.data.constant.Constants.POI_TABLE
import com.tiwa.taxi.data.model.Poi

@Dao
interface PoiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(poi: Poi?)

    @Transaction
    suspend fun updatePoiList(pois: List<Poi>) {
        deleteAll()
        insertAll(pois)
    }
    @Transaction
    suspend fun updatePoi(pois: Poi) {
        unselectPreviousPoi()
        insert(pois)
    }
    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM $POI_TABLE) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    suspend fun hasPoi(): Boolean

    @Insert
    fun insertAll(pois: List<Poi>)

    @Query("DELETE FROM $POI_TABLE ")
    suspend fun deleteAll()

    @Query("UPDATE $POI_TABLE SET isSelected = 0 WHERE isSelected = 1")
    suspend fun unselectPreviousPoi()

    @Query("SELECT * FROM $POI_TABLE ")
    fun getAllPois(): LiveData<List<Poi>>

    @Query("SELECT * FROM $POI_TABLE  WHERE isSelected = 1 ")
    fun getSelectedPoi(): LiveData<Poi>


}
