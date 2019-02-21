package com.ashley.data.weather.local

import com.ashley.data.ErrorMapper
import com.ashley.data.weather.WeatherEntityMapper
import com.ashley.data.weather.WeatherResponse
import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WeatherLocalRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherEntityMapper: WeatherEntityMapper,
    private val errorMapper: ErrorMapper
): WeatherLocalRepository {

    override fun insertWeather(weather: WeatherResponse): Completable {
        return weatherDao.insertWeather(weather)
    }

    override fun deleteWeather(weather: WeatherResponse): Completable {
        return weatherDao.delete(weather)
    }

    override fun updateWeather(weather: WeatherResponse): Completable {
        return weatherDao.update(weather)
    }

    override fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>> {
        return weatherDao.getWeatherByCoords(latitude, longitude)
            .map { WResult.Success(weatherEntityMapper.mapFrom(it)) as WResult<WeatherEntity> }
            .onErrorReturn { WResult.Failure(errorMapper.mapFrom(it)) }
    }
}