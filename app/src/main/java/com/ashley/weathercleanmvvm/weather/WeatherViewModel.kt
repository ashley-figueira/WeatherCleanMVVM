package com.ashley.weathercleanmvvm.weather

import com.ashley.domain.common.WError
import com.ashley.domain.entities.WeatherEntity
import com.ashley.domain.extensions.subscribeWeatherResult
import com.ashley.domain.usecases.GetWeatherUseCase
import com.ashley.weathercleanmvvm.R
import com.ashley.weathercleanmvvm.base.BaseViewModel
import com.ashley.weathercleanmvvm.base.ScreenState
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel<ScreenState<WeatherEntity>>() {

    private val locationSubject : SingleSubject<Array<Double>> = SingleSubject.create()

    init {
        locationSubject
            .flatMap { getWeatherUseCase(it.component1(), it.component2()).addToLoadingState() }
            .subscribeWeatherResult({ _screenState.value = ScreenState.success(it) }, {
                when (it) {
                    is WError.Offline -> _screenState.value = ScreenState.noInternet()
                    is WError.Timeout -> _screenState.value = ScreenState.error(R.string.error_timeout_message)
                    is WError.Unknown -> _screenState.value = ScreenState.error(R.string.error_loading_failed_message)
                }
            })
            .addToComposite()
    }

    fun loadWeather(coordinates: Array<Double>) {
        locationSubject.onSuccess(coordinates)
    }
}