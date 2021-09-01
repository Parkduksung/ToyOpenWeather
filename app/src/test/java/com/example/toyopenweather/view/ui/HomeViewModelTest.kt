package com.example.toyopenweather.view.ui

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.CityRepositoryImpl
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    lateinit var cityRepository: CityRepository


    private lateinit var homeViewModel: HomeViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        cityRepository = Mockito.mock(CityRepositoryImpl::class.java)
        homeViewModel = HomeViewModel(cityRepository)
    }


    @Test
    fun checkGetCityListSuccessStateTest() = runBlocking {

        val successResult = Result.success(CityListLocalDataSourceImplTest.mockCityList)

        Mockito.`when`(cityRepository.getCityList()).thenReturn(successResult)

        homeViewModel.getCityList()

    }

    @Test
    fun checkGetCityListFailStateTest() = runBlocking {

        val failResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(cityRepository.getCityList()).thenReturn(failResult)

        homeViewModel.getCityList()

    }

}