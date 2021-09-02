package com.example.toyopenweather.data.repo

import base.BaseTest
import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSource
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSourceImplTest.Companion.mockWeatherResponse
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito

class WeatherRepositoryImplTest : BaseTest() {

    @Mock
    lateinit var weatherRemoteDataSource: WeatherRemoteDataSource

    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl

    override fun createModules(): List<Module> {
        return listOf(
            module {
                single { weatherRemoteDataSource }
            }
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        weatherRepositoryImpl = WeatherRepositoryImpl()
    }

    @Test
    fun checkGetCurrentWeatherByIdSuccessTest() = runBlocking {

        val successResult = Result.success(mockWeatherResponse)

        Mockito.`when`(weatherRemoteDataSource.getCurrentWeatherById(id = 707860))
            .thenReturn(successResult)

        MatcherAssert.assertThat(
            "값을 올바르게 전달받았으므로 성공.",
            (weatherRepositoryImpl.getCurrentWeatherById(id = 707860) as Result.Success<WeatherResponse>).value,
            Matchers.`is`((successResult as Result.Success<WeatherResponse>).value)
        )

    }

    @Test
    fun checkGetCurrentWeatherByIdFailTest() = runBlocking {

        val failResult = Result.failure<WeatherResponse>(Throwable())

        Mockito.`when`(weatherRemoteDataSource.getCurrentWeatherById(id = 707860))
            .thenReturn(failResult)

        MatcherAssert.assertThat(
            "오류가 발생했으므로 실패.",
            (weatherRepositoryImpl.getCurrentWeatherById(id = 707860) as Result.Failure<WeatherResponse>).throwable.message,
            Matchers.`is`((failResult as Result.Failure<WeatherResponse>).throwable.message)
        )
    }

}