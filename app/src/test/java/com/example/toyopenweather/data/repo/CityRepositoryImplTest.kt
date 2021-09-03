package com.example.toyopenweather.data.repo

import base.BaseTest
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest
import com.example.toyopenweather.data.source.local.CityListLocalDataSourceImplTest.Companion.mockCityList
import com.example.toyopenweather.data.source.local.CityLocalDataSource
import com.example.toyopenweather.room.entity.CityEntity
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

        val successResult = Result.success(mockCityList)

        Mockito.`when`(cityLocalDataSource.getCityList()).thenReturn(successResult)

        MatcherAssert.assertThat(
            "값을 올바르게 전달받았으므로 성공.",
            (cityRepositoryImpl.getAssetCityList() as Result.Success<CityList>),
            Matchers.`is`(successResult)
        )
    }

    @Test
    fun checkGetCityListFailureTest() = runBlocking {

        val failResult = Result.failure<CityList>(Throwable())

        Mockito.`when`(cityLocalDataSource.getCityList()).thenReturn(failResult)

        MatcherAssert.assertThat(
            "예외가 발생했기 때문에 실패.",
            (cityRepositoryImpl.getAssetCityList() as Result.Failure<CityList>),
            Matchers.`is`(failResult)
        )
    }


    @Test
    fun checkGetCityEntitySuccessTest() = runBlocking {

        val mockCityEntity = mockCityList[0].toCityEntity()

        val successResult = Result.success(mockCityEntity)

        Mockito.`when`(cityLocalDataSource.getCityEntityByName("UA")).thenReturn(successResult)

        MatcherAssert.assertThat(
            "값을 올바르게 전달받았으므로 성공.",
            (cityRepositoryImpl.getCityEntity("UA") as Result.Success<CityEntity>).value,
            Matchers.`is`((successResult as Result.Success).value)
        )
    }

    @Test
    fun checkGetCityEntityFailureTest() = runBlocking {

        val failResult = Result.failure<CityEntity>(Throwable())

        Mockito.`when`(cityLocalDataSource.getCityEntityByName("UA")).thenReturn(failResult)

        MatcherAssert.assertThat(
            "예외가 발생했기 때문에 실패.",
            (cityRepositoryImpl.getCityEntity("UA") as Result.Failure<CityEntity>).throwable.message,
            Matchers.`is`((failResult as Result.Failure).throwable.message)
        )
    }

    @Test
    fun checkRegisterCityListSuccessTest() = runBlocking {

        val mockCityList = mockCityList

        Mockito.`when`(cityLocalDataSource.registerCityList(mockCityList)).thenReturn(true)

        MatcherAssert.assertThat(
            "등록이 잘됬으므로 성공.",
            (cityRepositoryImpl.registerCityList(mockCityList)),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkRegisterCityListFailureTest() = runBlocking {

        val mockCityList = mockCityList

        Mockito.`when`(cityLocalDataSource.registerCityList(mockCityList)).thenReturn(false)

        MatcherAssert.assertThat(
            "등록이 잘되지 않았으므로 성공.",
            (cityRepositoryImpl.registerCityList(mockCityList)),
            Matchers.`is`(false)
        )
    }

    @Test
    fun checkIsExistCityListSuccessTest() = runBlocking {

        Mockito.`when`(cityLocalDataSource.isExistCityList()).thenReturn(true)

        MatcherAssert.assertThat(
            "값을 존재하므로 성공.",
            (cityRepositoryImpl.isExistCityList()),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkIsExistCityListFailureTest() = runBlocking {

        Mockito.`when`(cityLocalDataSource.isExistCityList()).thenReturn(false)

        MatcherAssert.assertThat(
            "값을 존재하지 않으므로 실패.",
            (cityRepositoryImpl.isExistCityList()),
            Matchers.`is`(false)
        )
    }

    @Test
    fun checkGetAllCityEntitySuccessTest() = runBlocking {

        val mockCityEntityList = mockCityList.map { it.toCityEntity() }

        val successResult = Result.success(mockCityEntityList)

        Mockito.`when`(cityLocalDataSource.getAllCityEntity()).thenReturn(successResult)

        MatcherAssert.assertThat(
            "전체 등록된 값을 잘 가져오므로 성공.",
            (cityRepositoryImpl.getAllCityEntity() as Result.Success<List<CityEntity>>).value,
            Matchers.`is`((successResult as Result.Success).value)
        )
    }

    @Test
    fun checkGetAllCityEntityFailureTest() = runBlocking {

        val failResult = Result.failure<List<CityEntity>>(Throwable())

        Mockito.`when`(cityLocalDataSource.getAllCityEntity()).thenReturn(failResult)

        MatcherAssert.assertThat(
            "전체 등록된 값을 가져오지 못하므로 실패.",
            (cityRepositoryImpl.getAllCityEntity() as Result.Failure<List<CityEntity>>).throwable.message,
            Matchers.`is`((failResult as Result.Failure).throwable.message)
        )
    }

}