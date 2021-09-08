package com.example.toyopenweather.util

import android.annotation.SuppressLint
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
            Result.failure(Throwable("Error Read Asset File"))
        }

    companion object {
        private const val CITY_LIST_JSON = "citylist.json"

        @SuppressLint("StaticFieldLeak")
        private var instace: CityImpl? = null

        fun getInstance(
            context: Context
        ): City =
            instace ?: CityImpl(context).also {
                instace = it
            }
    }
}

interface City {
    fun getCityList(): Result<CityList>
}