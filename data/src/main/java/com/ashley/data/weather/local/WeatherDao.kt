package com.ashley.data.weather.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherRoomEntity): Completable

    @Query("SELECT * from Weather where lat = :lat AND lon = :lon")
    fun getWeatherByCoords(lat: Double, lon: Double): Single<WeatherRoomEntity>

    @Update
    fun update(weather: WeatherRoomEntity): Completable

    @Delete
    fun delete(weather: WeatherRoomEntity): Completable
}