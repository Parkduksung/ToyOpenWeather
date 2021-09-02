package com.example.toyopenweather.koin

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class KoinBaseSetup {

    protected abstract fun getModules(): List<Module>


    private fun createModules(): List<Module> {
        return mutableListOf<Module>().apply {
            addAll(getModules())
        }
    }

    fun setUp(context: Context) {
        startKoin {
            androidContext(context.applicationContext)
            modules(createModules())
        }
    }
}