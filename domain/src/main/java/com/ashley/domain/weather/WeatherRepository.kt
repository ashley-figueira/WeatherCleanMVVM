package com.ashley.domain.weather

import com.ashley.domain.common.WResult
import io.reactivex.Single

interface WeatherRepository {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>>
}