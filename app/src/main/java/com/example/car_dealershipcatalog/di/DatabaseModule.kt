package com.example.car_dealershipcatalog.di

import android.content.Context
import androidx.room.Room
import com.example.car_dealershipcatalog.data.local.VehicleDatabase
import com.example.car_dealershipcatalog.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VehicleDatabase {
        return Room.databaseBuilder(
            context,
            VehicleDatabase::class.java,
            Constant.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideVehicleDao(database: VehicleDatabase) = database.vehicleDao()
}
