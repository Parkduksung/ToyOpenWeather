package com.example.toyopenweather.koin

import com.example.toyopenweather.api.WeatherApi
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.CityRepositoryImpl
import com.example.toyopenweather.data.repo.WeatherRepository
import com.example.toyopenweather.data.repo.WeatherRepositoryImpl
import com.example.toyopenweather.data.source.local.CityLocalDataSource
import com.example.toyopenweather.data.source.local.CityLocalDataSourceImpl
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSource
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSourceImpl
import com.example.toyopenweather.util.City
import com.example.toyopenweather.util.CityImpl
import com.example.toyopenweather.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppKoinSetup : KoinBaseSetup() {

    private val viewModelModule = module {
        viewModel { HomeViewModel() }
    }

    private val repositoryModule = module {
        single<CityRepository> { CityRepositoryImpl() }
        single<WeatherRepository> { WeatherRepositoryImpl() }
    }

    private val sourceModule = module {
        single<CityLocalDataSource> { CityLocalDataSourceImpl() }
        single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl() }
    }

    private val apiModule = module {
        single<WeatherApi> {
            Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }

    private val assetModule = module {
        single<City> { CityImpl(get()) }
    }


    override fun getModules(): List<Module> {
        return listOf(
            viewModelModule,
            repositoryModule,
            sourceModule,
            apiModule,
            assetModule
        )
    }

    companion object {

        private const val WEATHER_BASE_URL = "https://api.openweathermap.org/"

    }


}