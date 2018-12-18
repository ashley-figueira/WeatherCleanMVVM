package com.ashley.data.weather

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Weather")
data class WeatherResponse(
    @ColumnInfo(name = "base") @SerializedName("base") var base: String? = null,
    @Ignore @SerializedName("clouds") var clouds: Clouds? = null,
    @Ignore @SerializedName("cod") var cod: Int? = null,
    @Embedded @SerializedName("coord") var coord: Coord? = null,
    @ColumnInfo(name = "lastUpdatedAt") @SerializedName("dt") var dt: Int? = null,
    @ColumnInfo(name = "id") @PrimaryKey @SerializedName("id") var id: Int? = null,
    @Embedded @SerializedName("main") var main: Main? = null,
    @ColumnInfo(name = "city") @SerializedName("name") var name: String? = null,
    @Ignore @SerializedName("sys") var sys: Sys? = null,
    @Ignore @SerializedName("visibility") var visibility: Int? = null,
    @ColumnInfo(name = "weather") @SerializedName("weather") var weather: List<Weather>? = null,
    @Embedded @SerializedName("wind") var wind: Wind? = null
)

data class Weather(
    @ColumnInfo(name = "description") @SerializedName("description") var description: String? = null,
    @ColumnInfo(name = "icon") @SerializedName("icon") var icon: String? = null,
    @Ignore @SerializedName("id") var id: Int? = null,
    @ColumnInfo(name = "main") @SerializedName("main") var main: String? = null
)

data class Main(
    @Ignore @SerializedName("humidity") var humidity: Int? = null,
    @Ignore @SerializedName("pressure") var pressure: Int? = null,
    @ColumnInfo(name = "temp") @SerializedName("temp") var temp: Double? = null,
    @Ignore @SerializedName("temp_max") var temp_max: Double? = null,
    @Ignore @SerializedName("temp_min") var temp_min: Double? = null
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
    @ColumnInfo(name = "lat") @SerializedName("lat") var lat: Double? = null,
    @ColumnInfo(name = "lon") @SerializedName("lon") var lon: Double? = null
)

data class Clouds(
    @SerializedName("all") var all: Int? = null
)

data class Wind(
    @ColumnInfo(name = "deg") @SerializedName("deg") var deg: Int? = null,
    @ColumnInfo(name = "speed") @SerializedName("speed") var speed: Double? = null
)