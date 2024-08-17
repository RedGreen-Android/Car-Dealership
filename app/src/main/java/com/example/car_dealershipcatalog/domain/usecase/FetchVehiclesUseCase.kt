package com.example.car_dealershipcatalog.domain.usecase

import com.example.car_dealershipcatalog.domain.model.Vehicle
import com.example.car_dealershipcatalog.domain.repository.VehicleRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class FetchVehiclesUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    //Remote fetch data, use case to encapsulate
    suspend operator fun invoke(): List<Vehicle> {
        return repository.fetchAndSaveVehicles()
    }

    //Get vehicles from the local database using Flowable
    fun getVehicles(): Flowable<List<Vehicle>> {
        return repository.getVehicles()
    }
}
