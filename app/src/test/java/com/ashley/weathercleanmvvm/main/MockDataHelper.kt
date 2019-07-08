package com.ashley.weathercleanmvvm.main

import com.ashley.data.weather.WeatherResponse
import com.ashley.data.weather.local.WeatherRoomEntity
import com.ashley.domain.usecases.WeatherEntity
import com.ashley.domain.usecases.WindDirection
import com.google.gson.Gson
import org.joda.time.DateTime

class MockDataHelper {
    companion object {

        val json = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":300,\"main\":\"Drizzle\"," +
                "\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":280.32," +
                "\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1," +
                "\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\"," +
                "\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"London\",\"cod\":200}"

        fun getWeatherResponse(): WeatherResponse = Gson().fromJson(json, WeatherResponse::class.java)

        fun getWeatherEntity(dateTime: DateTime = DateTime.now()) = WeatherEntity(1234, "London", 20.5f, "Sunny", 60.0, WindDirection.NorthEast, 55.0,50.5, "iconUrl", dateTime)

        fun getWeatherRoomEntity(dateTime: DateTime = DateTime.now()) = WeatherRoomEntity(1234, "London", 20.5f, "Sunny", 60.0, WindDirection.NorthEast.toString(), 55.0,50.5, "iconUrl", dateTime.millis)

        fun getLocation() = arrayOf(55.0, 55.0)
    }
}