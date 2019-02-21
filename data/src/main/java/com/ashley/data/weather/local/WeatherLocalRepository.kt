package com.ashley.data.weather.local

import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherLocalRepository {
    fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>>

    fun insertWeather(weather: WeatherRoomEntity): Completable

    fun deleteWeather(weather: WeatherRoomEntity): Completable

    fun updateWeather(weather: WeatherRoomEntity): Completable
}