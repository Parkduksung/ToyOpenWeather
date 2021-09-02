package com.example.toyopenweather.viewmodel

import androidx.lifecycle.Observer
import base.BaseTest
import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.WeatherRepository
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest.Companion.mockCityList
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSourceImplTest.Companion.mockWeatherResponse
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito

class HomeViewModelTest : BaseTest() {

    @Mock
    lateinit var cityRepository: CityRepository

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var homeViewStateObserver: Observer<HomeViewModel.HomeViewState>

    private lateinit var homeViewModel: HomeViewModel

    override fun createModules(): List<Module> {
        return listOf(
            module {
                single { cityRepository }
                single { weatherRepository }
            }
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        homeViewModel = HomeViewModel()
        homeViewModel.viewStateLiveData.observeForever(homeViewStateObserver)
    }


    @Test
    fun checkGetCityListSuccessStateTest() = runBlocking {

        val successResult = Result.success(mockCityList)

        Mockito.`when`(cityRepository.getCityList()).thenReturn(successResult)

        homeViewModel.getCityList()

        Mockito.verify(homeViewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.GetCityList(mockCityList))

    }

    @Test
    fun checkGetCityListFailStateTest() = runBlocking {

        val failResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(cityRepository.getCityList()).thenReturn(failResult)

        homeViewModel.getCityList()

        Mockito.verify(homeViewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCityList)

    }

    @Test
    fun checkGetCurrentWeatherByIdSuccessTest() = runBlocking {

        val successResult = Result.success(mockWeatherResponse)

        Mockito.`when`(weatherRepository.getCurrentWeatherById(id = 707860))
            .thenReturn(successResult)

        homeViewModel.getCurrentWeatherById(id = 707860)

        Mockito.verify(homeViewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.GetCurrentWeather((successResult as Result.Success).value))

    }

    @Test
    fun checkGetCurrentWeatherByIdFailTest() = runBlocking {

        val failResult = Result.failure<WeatherResponse>(Throwable())

        Mockito.`when`(weatherRepository.getCurrentWeatherById(id = 707860))
            .thenReturn(failResult)

        homeViewModel.getCurrentWeatherById(id = 707860)

        Mockito.verify(homeViewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCurrentWeather)


    }

}