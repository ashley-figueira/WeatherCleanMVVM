package com.ashley.domain.repositories

import com.ashley.domain.common.WResult
import com.ashley.domain.entities.WeatherEntity
import io.reactivex.Single

interface WeatherRepository {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>>
}