package com.example.car_dealershipcatalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_dealershipcatalog.domain.model.Vehicle
import com.example.car_dealershipcatalog.domain.usecase.FetchVehiclesUseCase
import com.example.car_dealershipcatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing vehicle data.
 *
 * @param fetchVehiclesUseCase Use case for fetching vehicles.
 */
@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val fetchVehiclesUseCase: FetchVehiclesUseCase
) : ViewModel() {

    private val _vehicleState = MutableStateFlow<Resource<List<Vehicle>>>(Resource.loading(null)) //I would have "State" class passed here but demonstrating RxJava flowables and Stateflows
    val vehicleState: StateFlow<Resource<List<Vehicle>>> = _vehicleState.asStateFlow()

    init {
        fetchVehicles()
    }

    /**
     * Fetches vehicles from the repository and updates the state.
     */
    private fun fetchVehicles() {
        _vehicleState.value = Resource.loading(null)

        viewModelScope.launch {
            try {
                val vehiclesList = fetchVehiclesUseCase.invoke()
                _vehicleState.value = Resource.success(vehiclesList)
            } catch (e: Exception) {
                _vehicleState.value = Resource.error(null, e.message)
            }
        }
    }

    // Collect Flowable and convert it to StateFlow for Compose integration
    fun getVehiclesFlowable(): Flowable<List<Vehicle>> {
        return fetchVehiclesUseCase.getVehicles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}