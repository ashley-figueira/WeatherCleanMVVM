package com.ashley.data.weather.remote

import com.ashley.data.weather.WeatherResponse
import io.reactivex.Single

interface WeatherRemoteRepository {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WeatherResponse>
}