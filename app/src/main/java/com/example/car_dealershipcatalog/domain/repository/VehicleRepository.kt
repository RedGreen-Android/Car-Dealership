package com.example.car_dealershipcatalog.domain.repository

import com.example.car_dealershipcatalog.domain.model.Vehicle
import io.reactivex.rxjava3.core.Flowable

interface VehicleRepository {
    fun getVehicles(): Flowable<List<Vehicle>>
    suspend fun fetchAndSaveVehicles(): List<Vehicle>
}
