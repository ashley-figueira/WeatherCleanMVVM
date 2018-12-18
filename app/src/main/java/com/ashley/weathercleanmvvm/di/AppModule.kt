package com.ashley.weathercleanmvvm.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ashley.domain.common.ResourceProvider
import com.ashley.domain.common.SchedulerProvider
import com.ashley.domain.di.ApplicationContext
import com.ashley.domain.di.PerApplication
import com.ashley.weathercleanmvvm.ViewModelProviderFactory
import com.ashley.weathercleanmvvm.WeatherApplication
import com.ashley.weathercleanmvvm.common.ResourceProviderImpl
import com.ashley.weathercleanmvvm.common.SchedulerProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun provideContext(application: WeatherApplication): Context

    @Binds
    @PerApplication
    abstract fun schedulerProvider(schedulerProviderImpl: SchedulerProviderImpl) : SchedulerProvider

    @Binds
    @PerApplication
    abstract fun resourceProvider(resourceProviderImpl: ResourceProviderImpl) : ResourceProvider

    @Binds
    @PerApplication
    abstract fun provideViewModelProvider(viewModelProvider: ViewModelProviderFactory): ViewModelProvider.Factory
}