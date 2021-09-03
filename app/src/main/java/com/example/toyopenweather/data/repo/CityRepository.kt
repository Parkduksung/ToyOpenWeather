package com.example.toyopenweather.data.repo

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.room.entity.CityEntity
import com.example.toyopenweather.util.Result

interface CityRepository {
    suspend fun getAssetCityList(): Result<CityList>

    suspend fun getCityEntity(cityName: String): Result<CityEntity>

    suspend fun registerCityList(cityList: CityList): Boolean

    suspend fun isExistCityList(): Boolean

    suspend fun getAllCityEntity(): Result<List<CityEntity>>
}