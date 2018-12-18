package com.ashley.data.weather.local

import androidx.room.TypeConverter
import com.ashley.data.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {

    @TypeConverter
    fun stringToWeatherList(data: String?): List<Weather> {
        if (data == null) return emptyList()

        val gson = Gson()
        val listType = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun weatherListToString(someObjects: List<Weather>): String {
        return Gson().toJson(someObjects)
    }
}