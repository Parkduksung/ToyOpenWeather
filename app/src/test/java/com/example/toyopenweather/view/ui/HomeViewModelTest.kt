package com.example.toyopenweather.view.ui

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.CityRepositoryImpl
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.runBlocking
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
        cityRepository = Mockito.mock(CityRepositoryImpl::class.java)
        homeViewModel = HomeViewModel(cityRepository)
    }


    @Test
    fun checkGetCityListSuccessStateTest() = runBlocking {

        val successResult = Result.success(CityListLocalDataSourceImplTest.mockCityList)

        Mockito.`when`(cityRepository.getCityList()).thenReturn(successResult)

        homeViewModel.getCityList()

        Mockito.verify(homeViewModel.viewStateLiveData)
            .onChanged(HomeViewModel.HomeViewState.GetCityList(CityListLocalDataSourceImplTest.mockCityList.toList()))

    }

    @Test
    fun checkGetCityListFailStateTest() = runBlocking {

        val failResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(cityRepository.getCityList()).thenReturn(failResult)

        Mockito.verify(homeViewModel.viewStateLiveData)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCityList)

    }

}