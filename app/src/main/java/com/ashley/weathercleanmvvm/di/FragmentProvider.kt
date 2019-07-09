package com.ashley.weathercleanmvvm.di

import com.ashley.weathercleanmvvm.weather.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideWeatherFragment(): WeatherFragment
}