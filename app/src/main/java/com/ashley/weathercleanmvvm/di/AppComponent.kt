package com.ashley.weathercleanmvvm.di

import com.ashley.data.DataModule
import com.ashley.data.weather.WeatherDataModule
import com.ashley.domain.di.PerApplication
import com.ashley.weathercleanmvvm.WeatherApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    WeatherDataModule::class,
    DataModule::class,
    ActivityBuilder::class
])
@PerApplication
abstract class AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}