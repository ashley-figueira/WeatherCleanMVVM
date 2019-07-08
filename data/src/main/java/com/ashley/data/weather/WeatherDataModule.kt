package com.ashley.data.weather

import com.ashley.domain.di.PerApplication
import com.ashley.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class WeatherDataModule {

    @Binds
    @PerApplication
    abstract fun providesWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository
}