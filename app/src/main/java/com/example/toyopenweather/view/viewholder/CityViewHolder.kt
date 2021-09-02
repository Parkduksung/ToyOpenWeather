package com.example.toyopenweather.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyopenweather.R
import com.example.toyopenweather.data.model.CityItem

class CityViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
) {
    private val cityName: TextView = itemView.findViewById(R.id.item_city_name)

    fun bind(cityItem: CityItem) {
        cityName.text = cityItem.name
    }
}