package com.example.toyopenweather.util

import android.content.Context
import com.example.toyopenweather.data.model.CityList
import com.google.gson.Gson


class CityImpl(private val context: Context) : City {
    override fun getCityList(): Result<CityList> =
        try {
            Result.success(
                Gson().fromJson(
                    context.assets.open(CITY_LIST_JSON).bufferedReader().use { it.readLine() },
                    CityList::class.java
                )
            )
        } catch (e: Exception) {
            Result.failure(e.cause!!)
        }

    companion object {
        private const val CITY_LIST_JSON = "citylist.json"
    }
}

interface City {
    fun getCityList(): Result<CityList>
}