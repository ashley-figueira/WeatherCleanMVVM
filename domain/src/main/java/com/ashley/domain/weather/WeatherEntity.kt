package com.ashley.domain.weather

import org.joda.time.DateTime

data class WeatherEntity(
    val id: Long,
    val city: String,
    val temperature: Float,
    val condition: String,
    val wind: Double,
    val windDirection: WindDirection,
    val latitude: Double,
    val longitude: Double,
    val iconUrl: String,
    val lastUpdatedAt: DateTime
)