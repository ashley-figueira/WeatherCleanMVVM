package com.ashley.weathercleanmvvm.base

import androidx.annotation.StringRes
import com.ashley.domain.weather.WeatherEntity

sealed class ScreenState {

    class LoadingState(val isLoading: Boolean) : ScreenState()

    object EmptyState : ScreenState()

    object NoWifiState : ScreenState()

    class HasData(val data: WeatherEntity) : ScreenState()

    class Error(@StringRes val errorMessageId: Int) : ScreenState()
}