package com.ashley.data.weather

import com.ashley.data.common.ErrorMapper
import com.ashley.data.weather.local.WeatherDao
import com.ashley.data.weather.remote.WeatherService
import com.ashley.domain.common.WResult
import com.ashley.domain.entities.WeatherEntity
import com.ashley.domain.extensions.doOnWeatherSuccess
import com.ashley.domain.extensions.isOutOfDate
import com.ashley.domain.extensions.toResult
import com.ashley.domain.repositories.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    private val weatherEntityMapper: WeatherEntityMapper,
    private val errorMapper: ErrorMapper
): WeatherRepository {

    override fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>> {
        val latitude = "%.2f".format(latitude).toDouble()
        val longitude = "%.2f".format(longitude).toDouble()

        val cache = weatherDao.getWeatherByCoords(latitude, longitude)
                .map { weatherEntityMapper.mapFrom(it).toResult() }
                .onErrorReturn { errorMapper.mapFrom(it).toResult() }

        val network = weatherService.getWeatherByCoords(latitude, longitude)
                .map { weatherEntityMapper.mapFrom(it).toResult() }
                .onErrorReturn { errorMapper.mapFrom(it).toResult() }
                .doOnWeatherSuccess { weatherDao.insertWeather(weatherEntityMapper.mapFrom(it)).subscribe() }

        return Single.concat(cache, network)
            .filter { it is WResult.Success && it.data.lastUpdatedAt.isOutOfDate().not() }
            .firstOrError()
            .onErrorResumeNext(network)
    }
}