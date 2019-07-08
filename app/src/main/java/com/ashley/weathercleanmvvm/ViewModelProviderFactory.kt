package com.ashley.weathercleanmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashley.domain.di.PerApplication
import com.ashley.domain.usecases.GetWeatherUseCase
import com.ashley.weathercleanmvvm.common.FusedLocationHandler
import com.ashley.weathercleanmvvm.common.NetworkConnectivityHandler
import com.ashley.weathercleanmvvm.main.MainViewModel
import javax.inject.Inject

@PerApplication
class ViewModelProviderFactory @Inject constructor(
        private val locationHandler: FusedLocationHandler,
        private val networkHandler: NetworkConnectivityHandler,
        private val getWeatherUseCase: GetWeatherUseCase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(locationHandler, networkHandler, getWeatherUseCase)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}