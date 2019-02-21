package com.ashley.data.weather.local

import com.ashley.data.weather.WeatherResponse
import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherLocalRepository {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>>

    fun insertWeather(weather: WeatherResponse): Completable

    fun deleteWeather(weather: WeatherResponse): Completable

    fun updateWeather(weather: WeatherResponse): Completable
}