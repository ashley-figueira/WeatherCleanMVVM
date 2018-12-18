package com.ashley.data.weather

import com.ashley.data.utils.MockDataHelper
import com.ashley.data.weather.remote.WeatherRemoteRepositoryImpl
import com.ashley.data.weather.remote.WeatherService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRemoteRepositoryImplTest {

    private lateinit var weatherRemoteRepositoryImpl: WeatherRemoteRepositoryImpl
    private val weatherService: WeatherService = mock()
    private val weatherResponse: WeatherResponse = MockDataHelper.getWeatherResponse()

    @Before
    fun setUp() {
        weatherRemoteRepositoryImpl = WeatherRemoteRepositoryImpl(weatherService)
    }

    @Test
    fun testGetWeatherByCity() {
        whenever(weatherService.getWeatherByCity(anyString(), anyString())).thenReturn(Single.just(weatherResponse))

        val testObserver = weatherRemoteRepositoryImpl.getWeatherByCity("London").test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        verify(weatherService).getWeatherByCity(anyString(), anyString())
    }

    @Test
    fun testGetWeatherByCoords() {
        whenever(weatherService.getWeatherByCoords(anyDouble(), anyDouble(), anyString())).thenReturn(Single.just(weatherResponse))

        val testObserver = weatherRemoteRepositoryImpl.getWeatherByCoords(8.0, 55.0).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        verify(weatherService).getWeatherByCoords(anyDouble(), anyDouble(), anyString())
    }
}