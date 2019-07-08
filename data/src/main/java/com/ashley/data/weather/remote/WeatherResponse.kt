package com.ashley.data.weather.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        @SerializedName("base") var base: String? = null,
        @SerializedName("clouds") var clouds: Clouds? = null,
        @SerializedName("cod") var cod: Int? = null,
        @SerializedName("coord") var coord: Coord? = null,
        @SerializedName("dt") var dt: Int? = null,
        @SerializedName("id") var id: Int? = null,
        @SerializedName("main") var main: Main? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("sys") var sys: Sys? = null,
        @SerializedName("visibility") var visibility: Int? = null,
        @SerializedName("weather") var weather: List<Weather>? = null,
        @SerializedName("wind") var wind: Wind? = null
)

data class Weather(
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null
)

data class Main(
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("temp_max") var temp_max: Double? = null,
    @SerializedName("temp_min") var temp_min: Double? = null
)

data class Sys(
    @SerializedName("country") var country: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("message") var message: Double? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
    @SerializedName("type") var type: Int? = null
)

data class Coord(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null
)

data class Clouds(
    @SerializedName("all") var all: Int? = null
)

data class Wind(
    @SerializedName("deg") var deg: Int? = null,
    @SerializedName("speed") var speed: Double? = null
)