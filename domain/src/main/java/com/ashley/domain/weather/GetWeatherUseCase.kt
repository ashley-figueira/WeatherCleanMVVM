package com.ashley.domain.weather

import com.ashley.domain.common.SchedulerProvider
import com.ashley.domain.common.WResult
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val schedulerProvider: SchedulerProvider
) {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>> {
        return weatherRepository.getWeatherByCoords(latitude, longitude)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}