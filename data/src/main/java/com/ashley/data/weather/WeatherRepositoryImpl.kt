package com.ashley.data.weather

import com.ashley.data.ErrorMapper
import com.ashley.data.weather.local.WeatherLocalRepository
import com.ashley.data.weather.remote.WeatherRemoteRepository
import com.ashley.domain.common.WResult
import com.ashley.domain.extensions.isOutOfDate
import com.ashley.domain.weather.WeatherEntity
import com.ashley.domain.weather.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteRepositoryImpl: WeatherRemoteRepository,
    private val weatherLocalRepositoryImpl: WeatherLocalRepository,
    private val weatherEntityMapper: WeatherEntityMapper,
    private val errorMapper: ErrorMapper
): WeatherRepository {

    override fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>> {
        val latitude = "%.2f".format(latitude).toDouble()
        val longitude = "%.2f".format(longitude).toDouble()

        val cache = weatherLocalRepositoryImpl.getWeatherByCoords(latitude, longitude)
        val networkWithSave = weatherRemoteRepositoryImpl.getWeatherByCoords(latitude, longitude)
                .doOnSuccess { if (it is WResult.Success) weatherLocalRepositoryImpl.insertWeather(weatherEntityMapper.mapFrom(it.data)) }

        return Single.concat(cache, networkWithSave)
                .filter { it is WResult.Success && it.data.lastUpdatedAt.isOutOfDate().not() }
                .firstOrError()
                .onErrorResumeNext { networkWithSave }
    }
}