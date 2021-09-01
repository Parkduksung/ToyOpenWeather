package com.example.toyopenweather.data.source.remote

import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class WeatherRemoteDataSourceImplTest {

    @Mock
    lateinit var weatherApi: WeatherApi

    private lateinit var weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl

    @Before
    fun setUp() {

        weatherApi = Mockito.mock(WeatherApi::class.java)
        weatherRemoteDataSourceImpl = WeatherRemoteDataSourceImpl(weatherApi)
    }

    @Test
    fun checkGetCurrentWeatherByIdSuccessTest() = runBlocking {

        val successResult = Result.success(mockWeatherResponse)

        MatcherAssert.assertThat(
            "정상적으로 API 통신이 이뤄지므로 성공.",
            (weatherRemoteDataSourceImpl.getCurrentWeatherById(id = 707860) as Result.Success),
            Matchers.`is`(successResult)
        )

    }

    @Test
    fun checkGetCurrentWeatherByIdFailTest() = runBlocking {

        val failResult = Result.failure<Result<WeatherResponse>>(Throwable())

        Mockito.`when`(weatherApi.getCurrentWeatherById(id = 707860)).then {
            failResult
        }

        MatcherAssert.assertThat(
            "오류가 발생했으므로 실패.",
            (weatherRemoteDataSourceImpl.getCurrentWeatherById(id = 707860) as Result.Failure),
            Matchers.`is`(failResult)
        )
    }

    private fun initMockWeatherApi() {
        Mockito.`when`(weatherApi.getCurentWeatherById(id = 707860)).thenReturn(
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

}