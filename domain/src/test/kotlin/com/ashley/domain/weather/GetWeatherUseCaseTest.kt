package com.ashley.domain.weather

import com.ashley.domain.common.TestSchedulerProvider
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.ashley.domain.utils.MockDomainHelper
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
import java.net.SocketException

@RunWith(MockitoJUnitRunner::class)
class GetWeatherUseCaseTest {

    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private val weatherRepository: WeatherRepository = mock()

    @Before
    fun setUp() {
        getWeatherUseCase = GetWeatherUseCase(weatherRepository, TestSchedulerProvider())
    }

    @Test
    fun testGetWeatherByCoords_Success() {
        whenever(weatherRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDomainHelper.getWeatherEntity())))

        val testObserver = getWeatherUseCase.getWeatherByCoords(50.0, 44.4).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { value -> value is WResult.Success }

        verify(weatherRepository).getWeatherByCoords(anyDouble(), anyDouble())
    }

//    @Test
//    fun testGetWeatherByCity_Success() {
//        whenever(weatherRepository.getWeatherByCity(anyString()))
//                .thenReturn(Single.just(WResult.Success(MockDomainHelper.getWeatherEntity())))
//
//        val testObserver = getWeatherUseCase.getWeatherByCity("London").test()
//        testObserver.awaitTerminalEvent()
//        testObserver.assertNoErrors()
//        testObserver.assertValue { value -> value is WResult.Success }
//
//        verify(weatherRepository).getWeatherByCity(anyString())
//    }

    @Test
    fun testGetWeatherByCoords_Failure() {
        whenever(weatherRepository.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.Offline(SocketException()))))

        val testObserver = getWeatherUseCase.getWeatherByCoords(50.0, 44.4).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { value -> value is WResult.Failure }

        verify(weatherRepository).getWeatherByCoords(anyDouble(), anyDouble())
    }

//    @Test
//    fun testGetWeatherByCity_Failure() {
//        whenever(weatherRepository.getWeatherByCity(anyString()))
//                .thenReturn(Single.just(WResult.Failure(WError.Offline(SocketException()))))
//
//        val testObserver = getWeatherUseCase.getWeatherByCity("London").test()
//        testObserver.awaitTerminalEvent()
//        testObserver.assertNoErrors()
//        testObserver.assertValue { value -> value is WResult.Failure }
//
//        verify(weatherRepository).getWeatherByCity(anyString())
//    }
}