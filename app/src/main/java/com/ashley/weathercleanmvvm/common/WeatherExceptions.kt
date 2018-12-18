package com.ashley.weathercleanmvvm.common

import java.lang.Exception

abstract class WeatherExceptions : Exception() {

    class LocationRequestNotGrantedException : WeatherExceptions()

}