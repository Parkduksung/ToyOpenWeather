package com.example.toyopenweather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.CityRepositoryImpl
import com.example.toyopenweather.data.repo.WeatherRepository
import com.example.toyopenweather.data.repo.WeatherRepositoryImpl
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest.Companion.mockCityList
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSourceImplTest.Companion.mockWeatherResponse
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var cityRepository: CityRepository

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var homeViewStateObserver: Observer<HomeViewModel.HomeViewState>

    private lateinit var homeViewModel: HomeViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        cityRepository = Mockito.mock(CityRepositoryImpl::class.java)
        weatherRepository = Mockito.mock(WeatherRepositoryImpl::class.java)
        homeViewModel = HomeViewModel(cityRepository, weatherRepository)
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