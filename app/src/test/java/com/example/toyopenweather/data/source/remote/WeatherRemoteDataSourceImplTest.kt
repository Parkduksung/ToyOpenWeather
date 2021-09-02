package com.example.toyopenweather.data.source.remote

import base.BaseTest
import com.example.toyopenweather.api.WeatherApi
import com.example.toyopenweather.api.response.*
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRemoteDataSourceImplTest : BaseTest() {

    @Mock
    lateinit var weatherApi: WeatherApi

    private lateinit var weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl

    override fun createModules(): List<Module> {
        return listOf(
            module {
                single { weatherApi }
            }
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        weatherRemoteDataSourceImpl = WeatherRemoteDataSourceImpl()
        initMockWeatherApi()
    }

    @Test
    fun checkGetCurrentWeatherByIdSuccessTest() = runBlocking {

        val successResult = Result.success(mockWeatherResponse)

        MatcherAssert.assertThat(
            "정상적으로 API 통신이 이뤄지므로 성공.",
            (weatherRemoteDataSourceImpl.getCurrentWeatherById(id = 707860) as Result.Success<WeatherResponse>).value,
            Matchers.`is`((successResult as Result.Success<WeatherResponse>).value)
        )

    }

    @Test
    fun checkGetCurrentWeatherByIdFailTest() = runBlocking {

        val failResult = Result.failure<WeatherResponse>(Throwable())

        Mockito.`when`(weatherApi.getCurrentWeatherById(id = 707860)).then {
            failResult
        }

        MatcherAssert.assertThat(
            "오류가 발생했으므로 실패.",
            (weatherRemoteDataSourceImpl.getCurrentWeatherById(id = 707860) as Result.Failure<WeatherResponse>).throwable.message,
            Matchers.`is`((failResult as Result.Failure<WeatherResponse>).throwable.message)
        )
    }

    private fun initMockWeatherApi() {

        Assert.assertEquals(WeatherApi.WEATHER_BY_ID , "data/2.5/weather")
        Assert.assertEquals(WeatherApi.APP_KEY , "a07cb097858e46d4e01fbb55f39849d6")

        Mockito.`when`(weatherApi.getCurrentWeatherById(id = 707860)).thenReturn(
            object : Call<WeatherResponse> {
                override fun execute(): Response<WeatherResponse> {
                    return Response.success(mockWeatherResponse)
                }

                override fun enqueue(callback: Callback<WeatherResponse>) {
                    TODO("Not yet implemented")
                }

                override fun clone(): Call<WeatherResponse> {
                    TODO("Not yet implemented")
                }

                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            }
        )
    }


    companion object {
        val mockWeatherResponse = WeatherResponse(
            base = "stations",
            clouds = Clouds(all = 0),
            cod = 200,
            coord = Coord(lat = 44.55, lon = 34.2833),
            dt = 1630473046,
            id = 707860,
            main = Main(
                feels_like = 296.81,
                grnd_level = 1001,
                humidity = 56,
                pressure = 1013,
                sea_level = 1013,
                temp = 296.92,
                temp_max = 296.92,
                temp_min = 296.92
            ),
            name = "Gurzuf",
            sys = Sys(
                country = "UA",
                id = 2037874,
                sunrise = 1630465533,
                sunset = 1630513239,
                type = 2
            ),
            timezone = 10800,
            visibility = 10000,
            weather = listOf(
                Weather(
                    description = "clear sky",
                    icon = "01d",
                    id = 800,
                    main = "Clear"
                )
            ),
            wind = Wind(
                deg = 279,
                gust = 1.46,
                speed = 0.99
            )
        )
    }
}