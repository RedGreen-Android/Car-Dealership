package com.example.car_dealershipcatalog.di

import com.example.car_dealershipcatalog.domain.usecase.FetchVehiclesUseCase
import com.example.car_dealershipcatalog.domain.repository.VehicleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideFetchVehiclesUseCase(repository: VehicleRepository): FetchVehiclesUseCase {
        return FetchVehiclesUseCase(repository)
    }
}
