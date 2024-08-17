package com.example.car_dealershipcatalog.data.repository

import com.example.car_dealershipcatalog.data.remote.VehicleApiService
import com.example.car_dealershipcatalog.data.local.VehicleDao
import com.example.car_dealershipcatalog.data.local.toEntity
import com.example.car_dealershipcatalog.data.local.toVehicle
import com.example.car_dealershipcatalog.domain.model.Vehicle
import com.example.car_dealershipcatalog.domain.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val apiService: VehicleApiService,
    private val vehicleDao: VehicleDao
) : VehicleRepository {

    override fun getVehicles(): Flowable<List<Vehicle>> {
        return vehicleDao.getAllVehicles()
            .map { entities ->
                entities.map { it.toVehicle() }
            }
            .subscribeOn(Schedulers.io())
    }

    override suspend fun fetchAndSaveVehicles(): List<Vehicle> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVehicles().body()?.listings ?: emptyList()
            vehicleDao.clearVehicles()
            vehicleDao.insertAll(response.map { it.toEntity() })
            response
        }
    }
}

