package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.Coord
import com.example.toyopenweather.util.Result
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
    lateinit var cityLocalDataSource: CityLocalDataSource

    private val cityLocalDataSourceImpl by lazy { cityLocalDataSource }


    @Test
    fun checkGetCityListSuccessTest() = runBlocking {

        val successResult = Result.success(mockCityList)

        Mockito.`when`(cityLocalDataSource.getCityList()).thenReturn(successResult)

        MatcherAssert.assertThat(
            "데이터가 존재하므로 성공.",
            (cityLocalDataSourceImpl.getCityList() as Result.Success<List<CityItem>>),
            Matchers.`is`(successResult)
        )
    }

    @Test
    fun checkGetCityListFailureTest() = runBlocking {

        val failResult = Result.failure<List<CityItem>>(Throwable())

        Mockito.`when`(cityLocalDataSource.getCityList()).thenReturn(failResult)

        MatcherAssert.assertThat(
            "예외가 발생했기 때문에 실패.",
            (cityLocalDataSourceImpl.getCityList() as Result.Failure<List<CityItem>>),
            Matchers.`is`(failResult)
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
        }.toList()

    }
}