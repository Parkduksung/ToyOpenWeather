package com.example.toyopenweather.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.toyopenweather.R

class CityDetailFragment : Fragment(R.layout.fragment_city_detail) {

    companion object {

        private const val KEY_CITY_ID = "key_city_id"

        fun newInstance(cityId: Int): CityDetailFragment =
            CityDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CITY_ID, cityId)
                }
            }
    }

}