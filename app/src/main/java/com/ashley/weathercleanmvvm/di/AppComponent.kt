package com.ashley.weathercleanmvvm.di

import com.ashley.data.common.DataModule
import com.ashley.data.weather.WeatherDataModule
import com.ashley.domain.di.PerApplication
import com.ashley.weathercleanmvvm.WeatherApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Component providing application wide singletons.
 * To call this make use of WeatherApplication.coreComponent or the
 * Activity.coreComponent extension function.
 */
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    WeatherDataModule::class,
    DataModule::class,
    ActivityProvider::class
])
@PerApplication
interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}