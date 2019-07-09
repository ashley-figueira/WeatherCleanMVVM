package com.ashley.weathercleanmvvm.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.ashley.domain.usecases.GetWeatherUseCase
import com.ashley.weathercleanmvvm.base.ScreenAction
import com.ashley.weathercleanmvvm.base.ScreenState
import com.ashley.weathercleanmvvm.weather.WeatherViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.jraska.livedata.test
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val getWeatherUseCase: GetWeatherUseCase = mock()
    private lateinit var weatherViewModel: WeatherViewModel
    private val actionStream: PublishRelay<ScreenAction> = PublishRelay.create()

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    @Before fun setUp() {
        weatherViewModel = WeatherViewModel(getWeatherUseCase)
    }

    @After fun tearDown() {
        verifyNoMoreInteractions(getWeatherUseCase)
    }

    @Test fun givenCoordinatesAreGivenThenWeatherIsLoaded() {
        whenever(getWeatherUseCase(anyDouble(), anyDouble())).thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        val testObserver = weatherViewModel.screenState.test()

        weatherViewModel.loadWeather(arrayOf(50.0, 55.0))

        testObserver
            .assertHasValue()
            .assertHistorySize(3)
            .assertValue { it is ScreenState.Success }
            .assertNever { it is ScreenState.NoInternet }
            .assertNever { it is ScreenState.Error }

        verify(getWeatherUseCase).invoke(anyDouble(), anyDouble())
    }

    @Test fun givenUserRefreshesThenWeatherIsLoadedForASecondTime() {
        whenever(getWeatherUseCase(anyDouble(), anyDouble())).thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        val testObserver = weatherViewModel.screenState.test()

        weatherViewModel.loadWeather(arrayOf(50.0, 55.0))
        weatherViewModel.attach(actionStream)
        actionStream.accept(ScreenAction.PullToRefreshAction)

        testObserver
                .assertHasValue()
                .assertHistorySize(6)
                .assertValue { it is ScreenState.Success }
                .assertNever { it is ScreenState.NoInternet }
                .assertNever { it is ScreenState.Error }

        verify(getWeatherUseCase, times(2)).invoke(anyDouble(), anyDouble())
    }

    @Test fun givenUserIsOfflineThenShowOfflineState() {
        whenever(getWeatherUseCase(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.Offline(Exception()))))

        val testObserver = weatherViewModel.screenState.test()

        weatherViewModel.loadWeather(arrayOf(50.0, 55.0))

        testObserver
                .assertHasValue()
                .assertHistorySize(3)
                .assertNever { it is ScreenState.Success }
                .assertValue { it is ScreenState.NoInternet }
                .assertNever { it is ScreenState.Error }

        verify(getWeatherUseCase).invoke(anyDouble(), anyDouble())
    }

}