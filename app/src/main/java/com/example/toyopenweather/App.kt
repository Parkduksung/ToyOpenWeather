package com.example.toyopenweather

import android.app.Application
import com.example.toyopenweather.koin.AppKoinSetup
import com.example.toyopenweather.koin.KoinBaseSetup

class App : Application() {

    private val koinSetup: KoinBaseSetup = AppKoinSetup()

    override fun onCreate() {
        super.onCreate()
        koinSetup.setUp(this)
    }
}