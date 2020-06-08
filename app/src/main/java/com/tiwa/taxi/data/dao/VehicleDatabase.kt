package com.tiwa.taxi.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tiwa.taxi.data.model.Poi
import com.tiwa.taxi.data.typeconverter.CoordinateTypeConverter
import kotlinx.coroutines.CoroutineScope


@Database(entities = [ Poi::class], version = 2, exportSchema = false)
@TypeConverters(CoordinateTypeConverter::class)

abstract class VehicleDatabase : RoomDatabase() {

    abstract fun poiDao(): PoiDao

    companion object {
        @Volatile
        private var INSTANCE: VehicleDatabase? = null
        private var scope: CoroutineScope? = null

        fun getDatabase(context: Context, scope: CoroutineScope): VehicleDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDatabase::class.java,
                    "vehicle_database.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                this.scope = scope
                return instance

            }

        }
    }


}




