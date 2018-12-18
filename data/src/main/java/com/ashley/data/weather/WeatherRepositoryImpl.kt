package com.ashley.data.weather

import com.ashley.data.ErrorMapper
import com.ashley.data.weather.local.WeatherLocalRepository
import com.ashley.data.weather.remote.WeatherRemoteRepository
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import com.ashley.domain.weather.WeatherRepository
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteRepositoryImpl: WeatherRemoteRepository,
    private val weatherLocalRepositoryImpl: WeatherLocalRepository,
    private val weatherEntityMapper: WeatherEntityMapper,
    private val errorMapper: ErrorMapper
): WeatherRepository {

    override fun getWeatherByCoords(latitude: Double, longitude: Double): Single<WResult<WeatherEntity>> {
        return weatherLocalRepositoryImpl.getWeatherByCoords("%.2f".format(latitude).toDouble(), "%.2f".format(longitude).toDouble())
            .flatMap { result ->
                if (result is WResult.Success) {
                    result.data.lastUpdatedAt?.let {
                        if (it.isBefore(DateTime.now().minusDays(1))) {
                            weatherRemoteRepositoryImpl.getWeatherByCoords(latitude, longitude)
                        } else {
                            Single.just(result)
                        }
                    }
                } else if (result is WResult.Failure && result.error is WError.NoWeatherInDatabase) {
                    weatherRemoteRepositoryImpl.getWeatherByCoords(latitude, longitude)
                } else {
                    Single.just(result)
                }
            }
            .flatMap {result ->
                if (result is WeatherResponse) {
                    weatherLocalRepositoryImpl.insertWeather(result)
                        .andThen(Single.just(result))
                        .map { WResult.Success(weatherEntityMapper.mapFrom(it)) as WResult<WeatherEntity> }
                } else {
                    Single.just(result as WResult<WeatherEntity>)
                }
            }
            .onErrorReturn { WResult.Failure(errorMapper.mapFrom(it)) }
    }
}