package com.ashley.data.weather.local

import androidx.room.*
import com.ashley.data.weather.WeatherResponse
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherResponse): Completable

    @Query("SELECT * from Weather where city = :city")
    fun getWeatherByCity(city: String): Single<WeatherResponse>

    @Query("SELECT * from Weather where lat = :lat AND lon = :lon")
    fun getWeatherByCoords(lat: Double, lon: Double): Single<WeatherResponse>

    @Update
    fun update(weather: WeatherResponse): Completable

    @Delete
    fun delete(weather: WeatherResponse): Completable
}