package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.room.database.CityDatabase
import com.example.toyopenweather.room.entity.CityEntity
import com.example.toyopenweather.util.City
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class CityLocalDataSourceImpl : CityLocalDataSource {

    private val city by inject<City>(City::class.java)

    private val cityDatabase by inject<CityDatabase>(CityDatabase::class.java)

    override suspend fun getCityList(): Result<CityList> = withContext(Dispatchers.IO) {
        return@withContext try {
            city.getCityList()
        } catch (e: Exception) {
            Result.failure(Throwable())
        }
    }

    override suspend fun getCityEntityByName(cityName: String): Result<CityEntity> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(cityDatabase.cityDao().getCityEntityByName(cityName))
            } catch (e: Exception) {
                Result.failure(Throwable())
            }
        }

    override suspend fun registerCityList(cityList: CityList): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val toCityEntityList = cityList.map { it.toCityEntity() }

                toCityEntityList.forEach {
                    registerEntity(it)
                }

                cityDatabase.cityDao().getAll().isNotEmpty()
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun getAllCityEntity(): Result<List<CityEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(cityDatabase.cityDao().getAll())
            } catch (e: Exception) {
                Result.failure(Throwable())
            }
        }

    override suspend fun isExistCityList(): Boolean = withContext(Dispatchers.IO) {
        return@withContext cityDatabase.cityDao().getAll().isNotEmpty()
    }

    override suspend fun registerEntity(cityEntity: CityEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext cityDatabase.cityDao().registerCityEntity(cityEntity) > 0
        }

}