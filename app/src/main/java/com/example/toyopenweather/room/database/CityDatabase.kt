package com.example.toyopenweather.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.toyopenweather.room.dao.CityDao
import com.example.toyopenweather.room.entity.CityEntity

@Database(entities = [CityEntity::class], version = 2)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}