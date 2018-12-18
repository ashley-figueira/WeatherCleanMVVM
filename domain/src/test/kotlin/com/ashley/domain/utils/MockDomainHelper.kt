package com.ashley.domain.utils

import com.ashley.domain.weather.WeatherEntity
import com.ashley.domain.weather.WindDirection
import org.joda.time.DateTime

class MockDomainHelper {
    companion object {
        fun getWeatherEntity() = WeatherEntity(1234, "London", 20.5f, "Sunny", 60.0, WindDirection.NorthEast, "iconUrl", DateTime.now())
    }
}