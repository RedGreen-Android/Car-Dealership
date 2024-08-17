package com.example.car_dealershipcatalog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}