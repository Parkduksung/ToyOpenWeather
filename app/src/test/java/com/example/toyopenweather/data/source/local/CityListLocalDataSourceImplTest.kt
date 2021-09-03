package com.example.toyopenweather.data.source.local

import base.BaseTest
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.model.Coord
import com.example.toyopenweather.room.dao.CityDao
import com.example.toyopenweather.room.database.CityDatabase
import com.example.toyopenweather.room.entity.CityEntity
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
import kotlin.test.assertEquals


class CityListLocalDataSourceImplTest : BaseTest() {


    @Mock
    lateinit var city: City

    @Mock
    lateinit var cityDatabase: CityDatabase

    @Mock
    lateinit var cityDao: CityDao

    private lateinit var cityLocalDataSourceImpl: CityLocalDataSource

    override fun createModules(): List<Module> {
        return listOf(
            module {
                single { cityDatabase }
                single { city }
            }
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        cityLocalDataSourceImpl = CityLocalDataSourceImpl()
        cityDao = Mockito.mock(CityDao::class.java)
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
            (cityLocalDataSourceImpl.getCityList() as Result.Failure<CityList>).throwable.message,
            Matchers.`is`((failResult as Result.Failure).throwable.message)
        )
    }

    @Test
    fun checkRegisterCityListSuccessTest() = runBlocking {

        val mockCityEntity = mockCityList.map { it.toCityEntity() }

        mockCityList.forEach {
            Mockito.`when`(cityDao.registerCityEntity(it.toCityEntity())).thenReturn(1)
            Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)
        }

        Mockito.`when`(cityDao.getAll()).thenReturn(mockCityEntity)

        assertEquals(cityDatabase.cityDao().getAll().isNotEmpty(), true)

        MatcherAssert.assertThat(
            "정상적으로 등록이 되었으므로 성공.",
            cityLocalDataSourceImpl.registerCityList(mockCityList),
            Matchers.`is`(true)
        )

    }

    @Test
    fun checkRegisterCityListFailTest() = runBlocking {

        mockCityList.forEach {
            Mockito.`when`(cityDao.registerCityEntity(it.toCityEntity())).thenReturn(-1)
            Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)
        }

        MatcherAssert.assertThat(
            "정상적으로 등록이 되지않았으므로 실패.",
            cityLocalDataSourceImpl.registerCityList(mockCityList),
            Matchers.`is`(false)
        )

    }

    @Test
    fun checkRegisterCityListExceptionFailTest() = runBlocking {

        val throwable = Throwable()

        mockCityList.forEach {
            Mockito.`when`(cityDao.registerCityEntity(it.toCityEntity())).then { throwable }
            Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)
        }

        MatcherAssert.assertThat(
            "에외가 발생했으므로 실패.",
            cityLocalDataSourceImpl.registerCityList(mockCityList),
            Matchers.`is`(false)
        )

    }

    @Test
    fun checkGetAllRegisterCityItemSuccessTest() = runBlocking {

        val toMockCityEntityList = mockCityList.map { it.toCityEntity() }

        Mockito.`when`(cityDao.getAll()).thenReturn(toMockCityEntityList)
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)

        val successResult = Result.success(toMockCityEntityList)

        MatcherAssert.assertThat(
            "정상적으로 저장되어있는 값을 가져왔으므로 성공.",
            (cityLocalDataSourceImpl.getAllCityEntity() as Result.Success).value,
            Matchers.`is`((successResult as Result.Success).value)
        )

    }

    @Test
    fun checkGetAllRegisterCityItemFailTest() = runBlocking {


        val failResult = Result.failure<List<CityEntity>>(Throwable())

        Mockito.`when`(cityDao.getAll()).then { failResult }
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)

        MatcherAssert.assertThat(
            "에외가 발생했으므로 실패.",
            (cityLocalDataSourceImpl.getAllCityEntity() as Result.Failure).throwable.message,
            Matchers.`is`((failResult as Result.Failure).throwable.message)
        )

    }

    @Test
    fun checkGetCityEntityByNameSuccessTest() = runBlocking {

        val toMockCityEntity = mockCityList[0].toCityEntity()

        Mockito.`when`(cityDao.getCityEntityByName("UA")).thenReturn(toMockCityEntity)
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)

        val successResult = Result.success(toMockCityEntity)

        MatcherAssert.assertThat(
            "정상적으로 저장되어있는 값을 가져왔으므로 성공.",
            (cityLocalDataSourceImpl.getCityEntityByName("UA") as Result.Success).value,
            Matchers.`is`((successResult as Result.Success).value)
        )

    }

    @Test
    fun checkGetCityEntityByNameFailTest() = runBlocking {

        val failResult = Result.failure<CityEntity>(Throwable())

        Mockito.`when`(cityDao.getCityEntityByName("UA")).then { failResult }
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)

        MatcherAssert.assertThat(
            "에외가 발생했으므로 실패.",
            (cityLocalDataSourceImpl.getCityEntityByName("UA") as Result.Failure).throwable.message,
            Matchers.`is`((failResult as Result.Failure).throwable.message)
        )

    }

    @Test
    fun checkIsExistCityListSuccessTest() = runBlocking {

        val toMockCityEntityList = mockCityList.map { it.toCityEntity() }

        Mockito.`when`(cityDao.getAll()).thenReturn(toMockCityEntityList)
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)

        MatcherAssert.assertThat(
            "저장된 값이 존재하므로 성공.",
            cityLocalDataSourceImpl.isExistCityList(),
            Matchers.`is`(true)
        )

    }

    @Test
    fun checkIsExistCityListFailTest() = runBlocking {

        val toMockCityEntityList = emptyList<CityEntity>()

        Mockito.`when`(cityDao.getAll()).thenReturn(toMockCityEntityList)
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)

        MatcherAssert.assertThat(
            "저장된 값이 존재하지 않으므로 실패.",
            cityLocalDataSourceImpl.isExistCityList(),
            Matchers.`is`(false)
        )

    }

    @Test
    fun checkRegisterEntitySuccessTest() = runBlocking {

        val toMockCityEntity = mockCityList[0].toCityEntity()


        Mockito.`when`(cityDao.registerCityEntity(toMockCityEntity)).thenReturn(1)
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)


        MatcherAssert.assertThat(
            "저장이 잘됬으므로 성공.",
            cityLocalDataSourceImpl.registerEntity(toMockCityEntity),
            Matchers.`is`(true)
        )

    }

    @Test
    fun checkRegisterEntityFailureTest() = runBlocking {

        val toMockCityEntity = mockCityList[0].toCityEntity()


        Mockito.`when`(cityDao.registerCityEntity(toMockCityEntity)).thenReturn(-1)
        Mockito.`when`(cityDatabase.cityDao()).thenReturn(cityDao)


        MatcherAssert.assertThat(
            "저장이 잘됬으므로 성공.",
            cityLocalDataSourceImpl.registerEntity(toMockCityEntity),
            Matchers.`is`(false)
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