package com.ashley.data.weather.remote

import com.ashley.data.weather.WeatherResponse
import io.reactivex.Single

interface WeatherRemoteRepository {
    fun getWeatherByCity(city: String): Single<WeatherResponse>

    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WeatherResponse>
}