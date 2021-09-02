package com.example.toyopenweather.data.repo

import base.BaseTest
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest
import com.example.toyopenweather.data.source.local.CityLocalDataSource
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

class CityRepositoryImplTest : BaseTest() {


    @Mock
    lateinit var cityLocalDataSource: CityLocalDataSource

    private lateinit var cityRepositoryImpl: CityRepository

    override fun createModules(): List<Module> {
        return listOf(
            module {
                single { cityLocalDataSource }
            }
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        cityRepositoryImpl = CityRepositoryImpl()
    }

    @Test
    fun checkGetCityListSuccessTest() = runBlocking {

        val successResult = Result.success(CityListLocalDataSourceImplTest.mockCityList)

        Mockito.`when`(cityLocalDataSource.getCityList()).thenReturn(successResult)

        MatcherAssert.assertThat(
            "값을 올바르게 전달받았으므로 성공.",
            (cityRepositoryImpl.getCityList() as Result.Success<CityList>),
            Matchers.`is`(successResult)
        )
    }

    @Test
    fun checkGetCityListFailureTest() = runBlocking {

        val failResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(cityLocalDataSource.getCityList()).thenReturn(failResult)

        MatcherAssert.assertThat(
            "예외가 발생했기 때문에 실패.",
            (cityRepositoryImpl.getCityList() as Result.Failure<CityList>),
            Matchers.`is`(failResult)
        )
    }


}