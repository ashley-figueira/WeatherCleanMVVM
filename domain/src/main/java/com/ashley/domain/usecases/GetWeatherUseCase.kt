package com.ashley.domain.usecases

import com.ashley.domain.common.SchedulerProvider
import com.ashley.domain.extensions.compose
import com.ashley.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val schedulerProvider: SchedulerProvider
) {
    operator fun invoke(latitude: Double, longitude: Double) = weatherRepository.getWeatherByCoords(latitude, longitude).compose(schedulerProvider)
}