package com.example.toyopenweather.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.Coord

@Entity(tableName = "city_table")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val no: Int = 0,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
) {
    fun toCityItem(): CityItem =
        CityItem(
            id = id,
            name = name,
            country = country,
            coord = Coord(lat, lon)
        )

}