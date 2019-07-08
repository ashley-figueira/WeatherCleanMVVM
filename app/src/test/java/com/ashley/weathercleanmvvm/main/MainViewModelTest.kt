package com.ashley.weathercleanmvvm.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import com.ashley.domain.usecases.GetWeatherUseCase
import com.ashley.weathercleanmvvm.base.ScreenState
import com.ashley.weathercleanmvvm.common.FusedLocationHandler
import com.ashley.weathercleanmvvm.common.NetworkConnectivityHandler
import com.ashley.weathercleanmvvm.common.WeatherExceptions
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val locationHandler: FusedLocationHandler = mock()
    private val networkHandler: NetworkConnectivityHandler = mock()
    private val getWeatherUseCase: GetWeatherUseCase = mock()
    private val lifecycleOwner: LifecycleOwner = mock()
    private val navigator: MainNavigator = mock()

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Before fun setUp() {
        mainViewModel = MainViewModel(locationHandler, networkHandler, getWeatherUseCase)
        mainViewModel.setNavigator(navigator)
    }

    @After fun tearDown() {
    }

    @Test fun testLoadWeatherData_Success() {

        whenever(locationHandler.listen()).thenReturn(Single.just(MockDataHelper.getLocation()))
        whenever(getWeatherUseCase.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        mainViewModel.onStart(lifecycleOwner)

        mainViewModel.screenState
                .test()
                .assertHasValue()
                .assertValue { it is ScreenState.HasData }
                .assertNever { it is ScreenState.NoWifiState }
                .assertNever { it is ScreenState.Error }
                .assertNever { it is ScreenState.EmptyState }

        verify(getWeatherUseCase).getWeatherByCoords(anyDouble(), anyDouble())
        verify(navigator, never()).requestPermissions(any())
    }

    @Test fun testLoadWeatherData_LoadingIndicators() {
        whenever(locationHandler.listen()).thenReturn(Single.just(MockDataHelper.getLocation()))
        whenever(getWeatherUseCase.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Success(MockDataHelper.getWeatherEntity())))

        val testObserver = mainViewModel.screenState.test()

        mainViewModel.onStart(lifecycleOwner)

        testObserver.assertHistorySize(3)

        val screenStates = testObserver.valueHistory()
        assertTrue { screenStates[0] is ScreenState.LoadingState }
        val loadingSpinner1 = screenStates[0] as ScreenState.LoadingState
        assertTrue { loadingSpinner1.isLoading }
        assertTrue { screenStates[1] is ScreenState.LoadingState }
        val loadingSpinner2 = screenStates[1] as ScreenState.LoadingState
        assertFalse { loadingSpinner2.isLoading }
        assertTrue { screenStates[2] is ScreenState.HasData }


        verify(getWeatherUseCase).getWeatherByCoords(anyDouble(), anyDouble())
        verify(navigator, never()).requestPermissions(any())
    }

    @Test fun testLoadWeatherData_NoWifiState() {
        whenever(locationHandler.listen()).thenReturn(Single.just(MockDataHelper.getLocation()))
        whenever(getWeatherUseCase.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.Offline(Exception()))))

        mainViewModel.onStart(lifecycleOwner)

        mainViewModel.screenState
                .test()
                .assertHasValue()
                .assertNever { it is ScreenState.HasData }
                .assertValue { it is ScreenState.NoWifiState }
                .assertNever { it is ScreenState.Error }
                .assertNever { it is ScreenState.EmptyState }

        verify(getWeatherUseCase).getWeatherByCoords(anyDouble(), anyDouble())
        verify(navigator, never()).requestPermissions(any())
    }

    @Test fun testLoadWeatherData_Error() {
        whenever(locationHandler.listen()).thenReturn(Single.just(MockDataHelper.getLocation()))
        whenever(getWeatherUseCase.getWeatherByCoords(anyDouble(), anyDouble()))
                .thenReturn(Single.just(WResult.Failure(WError.Unknown(Exception()))))

        mainViewModel.onStart(lifecycleOwner)

        mainViewModel.screenState
                .test()
                .assertHasValue()
                .assertNever { it is ScreenState.HasData }
                .assertNever { it is ScreenState.NoWifiState }
                .assertValue { it is ScreenState.Error }
                .assertNever { it is ScreenState.EmptyState }

        verify(getWeatherUseCase).getWeatherByCoords(anyDouble(), anyDouble())
        verify(navigator, never()).requestPermissions(any())
    }

    @Test fun testLoadWeatherData_LocationPermissionsNeeded() {
        whenever(locationHandler.listen()).thenReturn(Single.error(WeatherExceptions.LocationRequestNotGrantedException()))
        whenever(locationHandler.locationPermissions()).thenReturn(arrayOf("permissions"))

        mainViewModel.onStart(lifecycleOwner)

        mainViewModel.screenState
                .test()
                .assertHasValue()
                .assertNever { it is ScreenState.HasData }
                .assertNever { it is ScreenState.NoWifiState }
                .assertNever { it is ScreenState.Error }
                .assertNever { it is ScreenState.EmptyState }

        verify(navigator).requestPermissions(any())
        verify(getWeatherUseCase, never()).getWeatherByCoords(anyDouble(), anyDouble())
    }

    @Test fun testOnConnectToWifi_Click() {
        mainViewModel.onConnectToWifi()
        verify(navigator).goToWifiSettings()
    }

}