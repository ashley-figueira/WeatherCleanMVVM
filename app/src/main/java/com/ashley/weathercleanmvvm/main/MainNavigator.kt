package com.ashley.weathercleanmvvm.main

import com.ashley.weathercleanmvvm.base.BaseNavigator

interface MainNavigator : BaseNavigator {
    fun goToWifiSettings()
    fun requestPermissions(permissions: Array<String>)
}