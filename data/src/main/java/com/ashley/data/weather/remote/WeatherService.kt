package com.ashley.data.weather.remote

import com.ashley.data.BuildConfig
import com.ashley.data.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getWeatherByCoords(@Query("lat") lat: Double,
                           @Query("lon") lon: Double,
                           @Query("appid") id: String = BuildConfig.ApiKey): Single<WeatherResponse>
}