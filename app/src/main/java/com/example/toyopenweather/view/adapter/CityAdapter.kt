package com.example.toyopenweather.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.view.viewholder.CityViewHolder

class CityAdapter : RecyclerView.Adapter<CityViewHolder>() {

    private lateinit var itemClickListener: (item: CityItem) -> Unit
    private val cityItemList = mutableListOf<CityItem>()

    fun setOnItemClickListener(listener: (item: CityItem) -> Unit) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(parent, itemClickListener)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cityItemList[position])
    }

    override fun getItemCount(): Int =
        cityItemList.size

    fun addAll(list: List<CityItem>) {
        cityItemList.addAll(list)
        notifyDataSetChanged()
    }
}