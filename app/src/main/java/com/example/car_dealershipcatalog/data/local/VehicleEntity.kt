package com.example.car_dealershipcatalog.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.car_dealershipcatalog.domain.model.Dealer
import com.example.car_dealershipcatalog.domain.model.FirstPhoto
import com.example.car_dealershipcatalog.domain.model.Images
import com.example.car_dealershipcatalog.domain.model.Vehicle
import com.example.car_dealershipcatalog.utils.Constant

@Entity(tableName = Constant.VEHICLES_TABLE_NAME)
data class VehicleEntity(
    @PrimaryKey val id: String,
    val vin: String,
    val year: Int,
    val make: String,
    val model: String,
    val trim: String,
    val mileage: Int,
    val price: Double,
    val largeImage: String?,
    val mediumImage: String?,
    val smallImages: String?,
    val dealerName: String,
    val dealerPhone: String,
    val dealerAddress: String,
    val city: String,
    val state: String,
    val exteriorColor: String?,
    val interiorColor: String?,
    val driveType: String?,
    val transmission: String?,
    val engine: String?,
    val bodyType: String?
)

fun VehicleEntity.toVehicle(): Vehicle {
    return Vehicle(
        id = id,
        vin = vin,
        year = year,
        make = make,
        model = model,
        trim = trim,
        mileage = mileage,
        currentPrice = price,
        images = Images(
            firstPhoto = FirstPhoto(
                large = largeImage ?: "",
                medium = mediumImage?: "",
                small = smallImages?: "",
            )
        ),
        dealer = Dealer(
            name = dealerName,
            phone = dealerPhone,
            address = dealerAddress,
            city = city,
            state = state
        ),
        exteriorColor = exteriorColor ?: "Unknown",
        interiorColor = interiorColor ?: "Unknown",
        drivetype = driveType ?: "Unknown",
        transmission = transmission ?: "Unknown",
        engine = engine ?: "Unknown",
        bodytype = bodyType ?: "Unknown"
    )
}

fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = id,
        vin = vin,
        year = year,
        make = make,
        model = model,
        trim = trim,
        mileage = mileage,
        price = currentPrice,
        largeImage = images.firstPhoto.large,
        mediumImage = images.firstPhoto.medium,
        smallImages = images.firstPhoto.small,
        dealerName = dealer.name,
        dealerPhone = dealer.phone,
        dealerAddress = dealer.address,
        city = dealer.city,
        state = dealer.state,
        exteriorColor = exteriorColor,
        interiorColor = interiorColor,
        driveType = drivetype,
        transmission = transmission,
        engine = engine,
        bodyType = bodytype
    )
}