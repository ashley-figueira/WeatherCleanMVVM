package com.ashley.data.weather

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
        val iconUrl = from.weather?.first()?.icon ?: ""
        val lastUpdatedAt = from.dt?.let { DateTime(it.toLong() * 1000L) }

        return WeatherEntity(
            id.toLong(),
            city,
            convertKelvinInCelsius(temperature),
            condition,
            wind,
            convertDegreesToDirection(windDirection),
            iconUrl,
            lastUpdatedAt
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
}