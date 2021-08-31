package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.Coord
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CityLocalDataSourceImplTest {


    @Mock
    lateinit var cityLocalDataSourceImpl: CityLocalDataSource


    @Test
    fun checkGetCityListSuccessTest() = runBlocking {

        Mockito.`when`(cityLocalDataSourceImpl.getCityList()).thenReturn(mockCityList)

        MatcherAssert.assertThat(
            "데이터가 존재하므로 성공.",
            cityLocalDataSourceImpl.getCityList().isNotEmpty(),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkGetCityListFailureTest() = runBlocking {

        Mockito.`when`(cityLocalDataSourceImpl.getCityList()).thenThrow(Throwable())

        MatcherAssert.assertThat(
            "예외가 발생했기 때문에 실패.",
            cityLocalDataSourceImpl.getCityList(),
            Matchers.`is`(Throwable())
        )
    }


    companion object {

        private val mockCityList = mutableListOf<CityItem>().apply {
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