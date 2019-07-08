package com.ashley.data.weather

import androidx.room.EmptyResultSetException
import com.ashley.data.common.ErrorMapper
import com.ashley.data.utils.MockDataHelper
import com.ashley.data.weather.local.WeatherDao
import com.ashley.data.weather.remote.WeatherService
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryImplTest {

    private lateinit var weatherRepository: WeatherRepositoryImpl
    private val weatherDao: WeatherDao = mock()
    private val weatherService: WeatherService = mock()
    private val weatherEntityMapper: WeatherEntityMapper = WeatherEntityMapper()
    private val errorMapper: ErrorMapper = ErrorMapper()

    @Before
    fun setUp() {
        whenever(weatherDao.insertWeather(any())).thenReturn(Completable.complete())
        whenever(weatherDao.getWeatherByCoords(anyDouble(), anyDouble())).thenReturn(Single.just(MockDataHelper.getWeatherRoomEntity()))
        whenever(weatherService.getWeatherByCoords(anyDouble(), anyDouble(), anyString())).thenReturn(Single.just(MockDataHelper.getWeatherResponse()))

        weatherRepository = WeatherRepositoryImpl(weatherDao, weatherService, weatherEntityMapper, errorMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(weatherDao, weatherService)
    }

    @Test
    fun testGivenWeHaveWeatherInDb_thenWeDoNotGoToNetwork() {

        weatherRepository.getWeatherByCoords(55.0, 55.5).test()
            .assertNoErrors()
            .assertValueCount(1) //Only database values where emitted
            .assertValue { it is WResult.Success }

        verify(weatherDao).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherService).getWeatherByCoords(anyDouble(), anyDouble(), anyString())
        verify(weatherDao, never()).insertWeather(any()) //This is proof that it didnt go to network
    }

    @Test
    fun testGivenWeatherInDbIsOutOfDate_thenReturnFromNetwork_andSaveToDb() {
        whenever(weatherDao.getWeatherByCoords(anyDouble(), anyDouble())).thenReturn(Single.just(MockDataHelper.getWeatherRoomEntity(DateTime.now().minusDays(2))))

        weatherRepository.getWeatherByCoords(55.0, 55.5).test()
            .assertNoErrors()
            .assertValueCount(1) //Only network values where emitted
            .assertValue { it is WResult.Success }

        verify(weatherDao).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherService).getWeatherByCoords(anyDouble(), anyDouble(), anyString())
        verify(weatherDao).insertWeather(any())

    }

    @Test
    fun testGivenWeHaveNoWeatherInDb_thenReturnFromNetwork_andSaveToDb() {
        whenever(weatherDao.getWeatherByCoords(anyDouble(), anyDouble())).thenReturn(Single.error(EmptyResultSetException("No events in database!")))

        weatherRepository.getWeatherByCoords(55.0, 55.5).test()
            .assertNoErrors()
            .assertValueCount(1) //Only network values where emitted
            .assertValue { it is WResult.Success }

        verify(weatherDao).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherService).getWeatherByCoords(anyDouble(), anyDouble(), anyString())
        verify(weatherDao).insertWeather(any())
    }

    @Test
    fun testGivenWeAreOfflineAndHaveNoWeatherInDb_thenReturnAnOfflineError() {
        whenever(weatherDao.getWeatherByCoords(anyDouble(), anyDouble())).thenReturn(Single.error(EmptyResultSetException("No events in database!")))
        whenever(weatherService.getWeatherByCoords(anyDouble(), anyDouble(), anyString())).thenReturn(Single.error(UnknownHostException()))


        weatherRepository.getWeatherByCoords(55.0, 55.5).test()
            .assertNoErrors()
            .assertValueCount(1) //Only network values where emitted
            .assertValue { it is WResult.Failure && it.error is WError.Offline }

        verify(weatherDao).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherService).getWeatherByCoords(anyDouble(), anyDouble(), anyString())
        verify(weatherDao, never()).insertWeather(any())

    }
}