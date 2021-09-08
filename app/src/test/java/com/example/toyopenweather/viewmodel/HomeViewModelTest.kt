package com.example.toyopenweather.viewmodel

import base.ViewModelBaseTest
import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.WeatherRepository
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest.Companion.mockCityList
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSourceImplTest.Companion.mockWeatherResponse
import com.example.toyopenweather.room.entity.CityEntity
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertTrue

class HomeViewModelTest : ViewModelBaseTest() {

    @Mock
    lateinit var cityRepository: CityRepository

    @Mock
    lateinit var weatherRepository: WeatherRepository

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
        homeViewModel = HomeViewModel(app = application)
        homeViewModel.viewStateLiveData.observeForever(viewStateObserver)
    }


    @Test
    fun checkGetCityListSuccessStateTest() = runBlocking {

        val successExistResult = Result.success(mockCityList)
        val successGetAllCityEntityResult =
            Result.success(mockCityList.map { it.toCityEntity() }) as Result.Success


        Mockito.`when`(cityRepository.getAssetCityList()).thenReturn(successExistResult)
        Mockito.`when`(cityRepository.isExistCityList()).thenReturn(true)
        Mockito.`when`(cityRepository.getAllCityEntity()).thenReturn(successGetAllCityEntityResult)


        homeViewModel.getCityList()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.GetCityList(successGetAllCityEntityResult.value.map { it.toCityItem() }))

    }

    @Test
    fun checkGetCityListFailStateTest() = runBlocking {

        val failGetAssetCityListResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(cityRepository.getAssetCityList()).thenReturn(failGetAssetCityListResult)
        Mockito.`when`(cityRepository.isExistCityList()).thenReturn(false)


        homeViewModel.getCityList()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCityList)

    }

    @Test
    fun checkGetCityListListAndRegisterFailStateTest() = runBlocking {

        val successGetAssetCityListResult = Result.success(mockCityList) as Result.Success

        Mockito.`when`(cityRepository.getAssetCityList()).thenReturn(successGetAssetCityListResult)
        Mockito.`when`(cityRepository.isExistCityList()).thenReturn(false)
        Mockito.`when`(cityRepository.registerCityList(successGetAssetCityListResult.value))
            .thenReturn(false)


        homeViewModel.getCityList()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCityList)

    }

    @Test
    fun checkGetCityListListAndRegisterSuccessStateTest() = runBlocking {

        val successGetAssetCityListResult = Result.success(mockCityList) as Result.Success

        val successGerAllCityEntityResult =
            Result.success(successGetAssetCityListResult.value.map { it.toCityEntity() }) as Result.Success

        Mockito.`when`(cityRepository.getAssetCityList()).thenReturn(successGetAssetCityListResult)
        Mockito.`when`(cityRepository.isExistCityList()).thenReturn(false)
        Mockito.`when`(cityRepository.registerCityList(successGetAssetCityListResult.value))
            .thenReturn(true)

        Mockito.`when`(cityRepository.getAllCityEntity()).thenReturn(successGerAllCityEntityResult)


        homeViewModel.getCityList()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.GetCityList(successGerAllCityEntityResult.value.map { it.toCityItem() }))

    }

    @Test
    fun checkGetCityListListAndRegisterSuccessAndGetAllCityItemFailStateTest() = runBlocking {

        val successGetAssetCityListResult = Result.success(mockCityList) as Result.Success

        val failGerAllCityEntityResult =
            Result.failure<List<CityEntity>>(Throwable())

        Mockito.`when`(cityRepository.getAssetCityList()).thenReturn(successGetAssetCityListResult)
        Mockito.`when`(cityRepository.isExistCityList()).thenReturn(false)
        Mockito.`when`(cityRepository.registerCityList(successGetAssetCityListResult.value))
            .thenReturn(true)

        Mockito.`when`(cityRepository.getAllCityEntity()).thenReturn(failGerAllCityEntityResult)


        homeViewModel.getCityList()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCityList)

    }

    @Test
    fun checkGetCurrentWeatherByIdSuccessTest() = runBlocking {

        val successResult = Result.success(mockWeatherResponse)

        Mockito.`when`(weatherRepository.getCurrentWeatherById(id = 707860))
            .thenReturn(successResult)

        homeViewModel.getCurrentWeatherById(id = 707860)

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.GetCurrentWeather((successResult as Result.Success).value.toWeatherItem()))

    }

    @Test
    fun checkGetCurrentWeatherByIdFailTest() = runBlocking {

        val failResult = Result.failure<WeatherResponse>(Throwable())

        Mockito.`when`(weatherRepository.getCurrentWeatherById(id = 707860))
            .thenReturn(failResult)

        homeViewModel.getCurrentWeatherById(id = 707860)

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCurrentWeather)


    }

    @Test
    fun checkRouteContentTest() = runBlocking {

        homeViewModel.routeContent()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.RouteContent)

    }

    @Test
    fun checkRouteDetailTest() = runBlocking {

        homeViewModel.routeDetail(cityId = 1234)

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.RouteDetail(cityId = 1234))

    }

    @Test
    fun checkGetCityItemSuccessTest() = runBlocking {

        val mockEntity = mockCityList[0].toCityEntity()

        homeViewModel.inputCityLiveData.value = mockEntity.name

        val successResult = Result.success(mockEntity) as Result.Success



        Mockito.`when`(cityRepository.getCityEntity(mockEntity.name))
            .thenReturn(successResult)

        homeViewModel.getCityItem()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.GetCityItem(successResult.value.toCityItem()))

    }

    @Test
    fun checkGetCityItemFailTest() = runBlocking {


        homeViewModel.inputCityLiveData.value = "bb"

        val failResult = Result.Failure<CityEntity>(Throwable())

        Mockito.`when`(cityRepository.getCityEntity("bb"))
            .thenReturn(failResult)

        homeViewModel.getCityItem()

        Mockito.verify(viewStateObserver)
            .onChanged(HomeViewModel.HomeViewState.ErrorGetCityItem)


    }


}