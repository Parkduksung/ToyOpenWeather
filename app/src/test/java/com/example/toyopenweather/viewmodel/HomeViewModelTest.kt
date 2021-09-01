package com.example.toyopenweather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.CityRepositoryImpl
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest.Companion.mockCityList
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
    lateinit var homeViewStateObserver: Observer<HomeViewModel.HomeViewState>

    private lateinit var homeViewModel: HomeViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        cityRepository = Mockito.mock(CityRepositoryImpl::class.java)
        homeViewModel = HomeViewModel(cityRepository)
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

}