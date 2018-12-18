package com.ashley.data.weather

import com.ashley.data.weather.local.WeatherLocalRepository
import com.ashley.data.weather.local.WeatherLocalRepositoryImpl
import com.ashley.data.weather.remote.WeatherRemoteRepository
import com.ashley.data.weather.remote.WeatherRemoteRepositoryImpl
import com.ashley.domain.di.PerApplication
import com.ashley.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class WeatherDataModule {

    @Binds
    @PerApplication
    abstract fun providesWeatherLocalRepository(weatherLocalRepository: WeatherLocalRepositoryImpl): WeatherLocalRepository

    @Binds
    @PerApplication
    abstract fun providesWeatherRemoteRepository(weatherRemoteRepository: WeatherRemoteRepositoryImpl): WeatherRemoteRepository

    @Binds
    @PerApplication
    abstract fun providesWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository
}