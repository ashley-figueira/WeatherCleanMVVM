package com.ashley.data.weather.remote

import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import io.reactivex.Single

interface WeatherRemoteRepository {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>>
}