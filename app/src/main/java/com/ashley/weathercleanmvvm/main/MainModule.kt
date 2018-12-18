package com.ashley.weathercleanmvvm.main

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainViewModel(view: MainActivity, viewModelProvider: ViewModelProvider.Factory): MainViewModel {
        val vm = ViewModelProviders.of(view, viewModelProvider).get(MainViewModel::class.java)
        vm.setNavigator(view)
        vm.setLifeCycleOwner(view)
        return vm
    }
}