package com.example.car_dealershipcatalog.di

import com.example.car_dealershipcatalog.data.remote.VehicleApiService
import com.example.car_dealershipcatalog.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVehicleApiService(retrofit: Retrofit): VehicleApiService {
        return retrofit.create(VehicleApiService::class.java)
    }
}
