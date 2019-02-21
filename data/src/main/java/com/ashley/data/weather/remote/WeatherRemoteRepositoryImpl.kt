package com.ashley.data.weather.remote

import com.ashley.data.ErrorMapper
import com.ashley.data.weather.WeatherEntityMapper
import com.ashley.data.weather.WeatherResponse
import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import io.reactivex.Single
import javax.inject.Inject

class WeatherRemoteRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherEntityMapper: WeatherEntityMapper,
    private val errorMapper: ErrorMapper
): WeatherRemoteRepository {

    override fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>> {
        return weatherService.getWeatherByCoords(latitude, longitude)
                .map { WResult.Success(weatherEntityMapper.mapFrom(it)) as WResult<WeatherEntity> }
                .onErrorReturn { WResult.Failure(errorMapper.mapFrom(it)) }
    }
}