package com.example.toyopenweather.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseFragment
import com.example.toyopenweather.databinding.FragmentCityDetailBinding
import com.example.toyopenweather.viewmodel.HomeViewModel

class CityDetailFragment : BaseFragment<FragmentCityDetailBinding>(R.layout.fragment_city_detail) {

    private val homeViewModel by activityViewModels<HomeViewModel>()

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