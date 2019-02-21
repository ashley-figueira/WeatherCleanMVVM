package com.ashley.data.weather

import com.ashley.data.DataConfig
import com.ashley.data.weather.local.WeatherRoomEntity
import com.ashley.domain.common.Mapper
import com.ashley.domain.weather.WeatherEntity
import com.ashley.domain.weather.WindDirection
import org.joda.time.DateTime
import java.lang.Float.parseFloat
import java.text.DecimalFormat
import javax.inject.Inject

class WeatherEntityMapper @Inject constructor() : Mapper<WeatherResponse, WeatherEntity>() {

    override fun mapFrom(from: WeatherResponse): WeatherEntity {
        val id = from.id ?: throw IllegalArgumentException("Weather does not exist!")
        val city = from.name ?: throw IllegalArgumentException("City does not exist!")
        val temperature = from.main?.temp ?: throw IllegalArgumentException("Temperature does not exist!")
        val condition = from.weather?.first()?.main ?: throw IllegalArgumentException("Condition does not exist!")
        val wind = from.wind?.speed ?: throw IllegalArgumentException("Wind speed does not exist!")
        val windDirection = from.wind?.deg ?: throw IllegalArgumentException("Wind direction does not exist!")
        val iconUrl =  from.weather?.first()?.icon?.let { "${DataConfig.WEATHER_ICON_BASE_URL}$it.png" } ?:  ""
        val lastUpdatedAt = from.dt?.let { DateTime(it.toLong() * 1000L) } ?: throw IllegalArgumentException("Last updated info not available!")
        val latitude = from.coord?.lat ?: throw IllegalArgumentException("Weather coordinates does not exist!")
        val longitude = from.coord?.lon ?: throw IllegalArgumentException("Weather coordinates does not exist!")

        return WeatherEntity(
            id.toLong(),
            city,
            convertKelvinInCelsius(temperature),
            condition,
            wind,
            convertDegreesToDirection(windDirection),
            latitude,
            longitude,
            iconUrl,
            lastUpdatedAt
        )
    }

    fun mapFrom(from: WeatherRoomEntity): WeatherEntity {
        return WeatherEntity(
            from.id,
            from.city,
            from.temperature,
            from.condition,
            from.wind,
            mapDirection(from.windDirection),
            from.latitude,
            from.longitude,
            from.iconUrl,
            DateTime(from.lastUpdatedAt)
        )
    }

    fun mapFrom(from: WeatherEntity): WeatherRoomEntity {
        return WeatherRoomEntity(
                from.id,
                from.city,
                from.temperature,
                from.condition,
                from.wind,
                from.windDirection.toString(),
                from.latitude,
                from.longitude,
                from.iconUrl,
                from.lastUpdatedAt.millis
        )
    }
    /**
     * Convert kelvin to celcius
     * @param kelvin - temp in kelvin
     * @return celcius
     */
    private fun convertKelvinInCelsius(kelvin: Double): Float {
        val decimalFormat = DecimalFormat()
        decimalFormat.maximumFractionDigits = 2
        return parseFloat(decimalFormat.format((kelvin - 273.15).toFloat().toDouble()))
    }

    private fun convertDegreesToDirection(windDegree: Int): WindDirection {
        return when {
            windDegree >= 45 && windDegree < 90   -> WindDirection.NorthEast
            windDegree >= 90 && windDegree < 135  -> WindDirection.East
            windDegree >= 135 && windDegree < 180 -> WindDirection.SouthEast
            windDegree >= 180 && windDegree < 225 -> WindDirection.South
            windDegree >= 225 && windDegree < 270 -> WindDirection.SouthWest
            windDegree >= 270 && windDegree < 315 -> WindDirection.West
            windDegree >= 315 && windDegree < 360 -> WindDirection.NorthWest
            else -> WindDirection.North
        }
    }

    private fun mapDirection(direction: String): WindDirection {
        return when (direction) {
            WindDirection.NorthEast.toString() ->  WindDirection.NorthEast
            WindDirection.East.toString() ->  WindDirection.East
            WindDirection.SouthEast.toString() ->  WindDirection.SouthEast
            WindDirection.South.toString() ->  WindDirection.South
            WindDirection.SouthWest.toString() ->  WindDirection.SouthWest
            WindDirection.West.toString() ->  WindDirection.West
            WindDirection.NorthWest.toString() ->  WindDirection.NorthWest
            else -> throw IllegalArgumentException("Something went wrong!")
        }
    }
}