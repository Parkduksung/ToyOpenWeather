package com.example.toyopenweather.data.source.local

import base.BaseTest
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.model.Coord
import com.example.toyopenweather.util.City
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


class CityListLocalDataSourceImplTest : BaseTest() {


    @Mock
    lateinit var city: City

    private lateinit var cityLocalDataSourceImpl: CityLocalDataSource

    override fun createModules(): List<Module> {
        return listOf(
            module {
                single { city }
            }
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        cityLocalDataSourceImpl = CityLocalDataSourceImpl()
    }


    @Test
    fun checkGetCityListSuccessTest() = runBlocking {

        val successResult = Result.success(mockCityList)

        Mockito.`when`(city.getCityList()).thenReturn(successResult)

        MatcherAssert.assertThat(
            "데이터가 존재하므로 성공.",
            (cityLocalDataSourceImpl.getCityList() as Result.Success<CityList>),
            Matchers.`is`(successResult)
        )
    }

    @Test
    fun checkGetCityListFailureTest() = runBlocking {

        val failResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(city.getCityList()).thenReturn(failResult)

        MatcherAssert.assertThat(
            "예외가 발생했기 때문에 실패.",
            (cityLocalDataSourceImpl.getCityList() as Result.Failure<CityList>),
            Matchers.`is`(failResult)
        )
    }


    companion object {

        val mockCityList = CityList().apply {
            add(
                CityItem(
                    id = 707860,
                    name = "UA",
                    country = "UA",
                    coord = Coord(lat = 44.549999, lon = 34.283333)
                )
            )
        }

    }
}