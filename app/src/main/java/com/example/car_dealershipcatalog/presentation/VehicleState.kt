package com.example.car_dealershipcatalog.presentation

import com.example.car_dealershipcatalog.domain.model.Vehicle

//**Since I am Demonstrating RXJAVA, I Decided not to use this as
// with coroutines and StateFlow, no need to collect Flowable from RxJava
data class VehicleState(
    val isLoading: Boolean = true,
    val vehicles: List<Vehicle> = emptyList(),
    val errorMsg: String? = null
)