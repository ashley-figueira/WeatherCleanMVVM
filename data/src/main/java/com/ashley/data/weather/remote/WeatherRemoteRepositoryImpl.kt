package com.ashley.data.weather.remote

import com.ashley.data.weather.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject

class WeatherRemoteRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
): WeatherRemoteRepository {

    override fun getWeatherByCity(city: String): Single<WeatherResponse> {
        return weatherService.getWeatherByCity(city)
    }

    override fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WeatherResponse> {
        return weatherService.getWeatherByCoords(latitude, longitude)
    }
}