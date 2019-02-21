package com.ashley.data.weather

import com.ashley.data.ErrorMapper
import com.ashley.data.utils.MockDataHelper
import com.ashley.data.weather.local.WeatherDao
import com.ashley.data.weather.local.WeatherLocalRepositoryImpl
import com.ashley.data.weather.local.WeatherRoomEntity
import com.ashley.domain.common.WResult
import com.ashley.domain.weather.WeatherEntity
import com.ashley.domain.weather.WindDirection
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherLocalRepositoryImplTest {

    private lateinit var weatherLocalRepositoryImpl: WeatherLocalRepositoryImpl
    private val weatherDao: WeatherDao = mock()
    private val weatherEntityMapper: WeatherEntityMapper = WeatherEntityMapper()
    private val errorMapper: ErrorMapper = ErrorMapper()
    private val weatherRoomEntity: WeatherRoomEntity = MockDataHelper.getWeatherRoomEntity(DateTime(2018, 2,2,0,0))

    @Before
    fun setUp() {
        weatherLocalRepositoryImpl = WeatherLocalRepositoryImpl(weatherDao, weatherEntityMapper, errorMapper)
    }

    @Test
    fun testInsertWeather() {
        whenever(weatherDao.insertWeather(any())).thenReturn(Completable.complete())

        val testObserver = weatherLocalRepositoryImpl.insertWeather(weatherRoomEntity).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun testDeleteWeather() {
        whenever(weatherDao.delete(any())).thenReturn(Completable.complete())

        val testObserver = weatherLocalRepositoryImpl.deleteWeather(weatherRoomEntity).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun testUpdateWeather() {
        whenever(weatherDao.update(any())).thenReturn(Completable.complete())

        val testObserver = weatherLocalRepositoryImpl.updateWeather(weatherRoomEntity).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun testGetWeatherByCoords() {
        whenever(weatherDao.getWeatherByCoords(anyDouble(), anyDouble())).thenReturn(Single.just(weatherRoomEntity))

        val testObserver = weatherLocalRepositoryImpl.getWeatherByCoords(0.0, 55.55).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { value -> value is WResult.Success }

        val value = testObserver.events[0][0] as WResult.Success<WeatherEntity>

        //asserting some values
        assertEquals(value.data.city, "London")
        assertEquals(value.data.temperature, 20.5f)
        assertEquals(value.data.lastUpdatedAt.dayOfMonth, 2)
        assertEquals(value.data.lastUpdatedAt.monthOfYear, 2)
        assertEquals(value.data.lastUpdatedAt.year, 2018)
        assertEquals(value.data.windDirection, WindDirection.NorthEast)
    }
}