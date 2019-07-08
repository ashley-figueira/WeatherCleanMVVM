package com.ashley.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ashley.data.weather.local.WeatherDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDatabaseTest {

    private lateinit var weatherDatabase: WeatherDatabase

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, WeatherDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        weatherDatabase.close()
    }

    @Test
    fun testInsertWeather() {
        val weatherRoomEntity = MockDataHelper.getWeatherRoomEntity()

        weatherDatabase.weatherDao().insertWeather(weatherRoomEntity).test()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun testGetWeatherByCoords() {
        val weatherRoomEntity = MockDataHelper.getWeatherRoomEntity()

        //we need to wait that it finishes inserting the weather before we test the get
        weatherDatabase.weatherDao().insertWeather(weatherRoomEntity).test().awaitTerminalEvent()

        weatherDatabase.weatherDao().getWeatherByCoords(51.51, -0.13).test()
            .assertNoErrors()
            .assertValueCount(1)
    }
}