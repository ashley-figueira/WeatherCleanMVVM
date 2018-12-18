package com.ashley.weathercleanmvvm.di

import com.ashley.weathercleanmvvm.main.MainActivity
import com.ashley.weathercleanmvvm.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainModule::class)])
    internal abstract fun bindMainActivity(): MainActivity
}