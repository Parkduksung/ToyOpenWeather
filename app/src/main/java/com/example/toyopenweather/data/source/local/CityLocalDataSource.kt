package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.room.entity.CityEntity
import com.example.toyopenweather.util.Result

interface CityLocalDataSource {
    suspend fun getCityList(): Result<CityList>

    suspend fun getCityEntityByName(cityName: String): Result<CityEntity>

    suspend fun registerCityList(cityList: CityList): Boolean

    suspend fun registerEntity(cityEntity: CityEntity) : Boolean

    suspend fun getAllCityEntity(): Result<List<CityEntity>>

    suspend fun isExistCityList(): Boolean

}