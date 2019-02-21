package com.ashley.data.weather.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class WeatherRoomEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "temperature") val temperature: Float,
    @ColumnInfo(name = "condition") val condition: String,
    @ColumnInfo(name = "speed") val wind: Double,
    @ColumnInfo(name = "windDirection") val windDirection: String,
    @ColumnInfo(name = "lat") val latitude: Double,
    @ColumnInfo(name = "lon") val longitude: Double,
    @ColumnInfo(name = "iconUrl") val iconUrl: String,
    @ColumnInfo(name = "lastUpdatedAt") val lastUpdatedAt: Long
)