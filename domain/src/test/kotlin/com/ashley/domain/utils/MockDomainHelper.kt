package com.ashley.domain.utils

import com.ashley.domain.entities.WeatherEntity
import com.ashley.domain.entities.WindDirection
import org.joda.time.DateTime

class MockDomainHelper {
    companion object {
        fun getWeatherEntity() = WeatherEntity(1234, "London", 20.5f, "Sunny", 60.0, WindDirection.NorthEast, 50.0, 55.0,"iconUrl", DateTime.now())
    }
}