package com.example.car_dealershipcatalog.domain.model

data class Vehicle(
    val id: String,
    val vin: String,
    val year: Int,
    val make: String,
    val model: String,
    val trim: String,
    val mileage: Int,
    val currentPrice: Double,
    val images: Images,
    val dealer: Dealer,
    val exteriorColor: String,
    val interiorColor: String,
    val drivetype: String,
    val transmission: String,
    val engine: String,
    val bodytype: String
)


