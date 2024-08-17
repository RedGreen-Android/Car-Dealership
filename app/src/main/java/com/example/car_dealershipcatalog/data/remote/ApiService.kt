package com.example.car_dealershipcatalog.data.remote

import com.example.car_dealershipcatalog.domain.model.VehicleResponse
import com.example.car_dealershipcatalog.utils.Constant
import retrofit2.Response
import retrofit2.http.GET

interface VehicleApiService {
    @GET(Constant.API_ENDPOINT)
    suspend fun getVehicles(): Response<VehicleResponse>
}
