package com.ashley.weathercleanmvvm.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.ashley.domain.usecases.GetWeatherUseCase
import com.ashley.weathercleanmvvm.R
import com.ashley.weathercleanmvvm.base.BaseViewModel
import com.ashley.weathercleanmvvm.base.ScreenState
import com.ashley.weathercleanmvvm.common.FusedLocationHandler
import com.ashley.weathercleanmvvm.common.NetworkConnectivityHandler
import com.ashley.weathercleanmvvm.common.WeatherExceptions
import timber.log.Timber

class MainViewModel(
    private val locationHandler: FusedLocationHandler,
    private val networkHandler: NetworkConnectivityHandler,
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel<MainNavigator>() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        loadWeatherData()
    }

    fun loadWeatherData() {
        locationHandler
            .listen()
            .flatMap { getWeatherUseCase.getWeatherByCoords(it[0], it[1]) }
            .doOnSubscribe { _screenState.value = ScreenState.LoadingState(true) }
            .doOnSuccess { _screenState.value = ScreenState.LoadingState(false) }
            .subscribe({ result ->
                when (result) {
                    is WResult.Success -> _screenState.value = ScreenState.HasData(result.data)
                    is WResult.Failure -> handleError(result.error)
                }
            }, { throwable ->
                if (throwable is WeatherExceptions.LocationRequestNotGrantedException) {
                    mNavigator.requestPermissions(locationHandler.locationPermissions())
                } else {
                    Timber.e(throwable)
                }
            })
            .addToComposite()
    }

    fun onConnectToWifi() = mNavigator.goToWifiSettings()

    private fun handleError(error: WError) {
        when (error) {
            is WError.Offline -> _screenState.value = ScreenState.NoWifiState
            else -> _screenState.value = ScreenState.Error(R.string.main_screen_unknown_error)
        }
    }
}