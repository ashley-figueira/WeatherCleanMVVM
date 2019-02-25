package com.ashley.data.weather

import androidx.room.EmptyResultSetException
import com.ashley.data.ErrorMapper
import com.ashley.data.utils.MockDataHelper
import com.ashley.data.weather.local.WeatherLocalRepository
import com.ashley.data.weather.remote.WeatherRemoteRepository
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import kotlin.test.assertTrue

class WeatherRepositoryImplTest {

    private lateinit var weatherRepository: WeatherRepositoryImpl
    private val weatherRemoteRepository: WeatherRemoteRepository = mock()
    private val weatherLocalRepository: WeatherLocalRepository = mock()
    private val weatherEntityMapper: WeatherEntityMapper = WeatherEntityMapper()
    private val errorMapper: ErrorMapper = ErrorMapper()

    @Before
    fun setUp() {
        weatherRepository = WeatherRepositoryImpl(weatherRemoteRepository, weatherLocalRepository, weatherEntityMapper, errorMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(weatherRemoteRepository, weatherLocalRepository)
    }

    @Test
    fun testGetWeatherByCoords_WeHaveWeatherInDb() {
        whenever(weatherLocalRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))
        whenever(weatherRemoteRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        val testObserver = weatherRepository.getWeatherByCoords(55.0, 55.5).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1) //Only database values where emitted
        testObserver.assertValue { value -> value is WResult.Success }

        verify(weatherLocalRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherRemoteRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherLocalRepository, never()).insertWeather(any()) //This is proof that it didnt go to network


    }

    @Test
    fun testGetWeatherByCoords_weatherInDbIsOutOfDate_fetchFromNetwork() {

        whenever(weatherLocalRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity(DateTime(2018, 12, 1,0,0)))))

        whenever(weatherRemoteRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        whenever(weatherLocalRepository.insertWeather(any())).thenReturn(Completable.complete())

        val testObserver = weatherRepository.getWeatherByCoords(55.0, 55.5).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1) //Only network values where emitted
        testObserver.assertValue { value -> value is WResult.Success }

        verify(weatherLocalRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherRemoteRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherLocalRepository).insertWeather(any())

    }

    @Test
    fun testGetWeatherByCoords_WeHaveNoWeatherInDb_fetchFromNetwork() {
        whenever(weatherLocalRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.NoWeatherInDatabase(EmptyResultSetException("No weather in database!")))))

        whenever(weatherRemoteRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        whenever(weatherLocalRepository.insertWeather(any())).thenReturn(Completable.complete())

        val testObserver = weatherRepository.getWeatherByCoords(55.0, 55.5).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1) //Only network values where emitted
        testObserver.assertValue { value -> value is WResult.Success }

        verify(weatherLocalRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherRemoteRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherLocalRepository).insertWeather(any())
    }

    @Test
    fun testGetWeatherByCoords_offline() {

        whenever(weatherLocalRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.NoWeatherInDatabase(EmptyResultSetException("No weather in database!")))))

        whenever(weatherRemoteRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.Offline(Exception()))))

        whenever(weatherLocalRepository.insertWeather(any())).thenReturn(Completable.complete())

        val testObserver = weatherRepository.getWeatherByCoords(55.0, 55.5).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1) //Only network values where emitted
        testObserver.assertValue { value -> value is WResult.Failure }

        val value = testObserver.events[0][0] as WResult.Failure<*>

        assertTrue { value.error is WError.Offline }

        verify(weatherLocalRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherRemoteRepository).getWeatherByCoords(anyDouble(), anyDouble())
        verify(weatherLocalRepository, never()).insertWeather(any())

    }
}