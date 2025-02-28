package com.stats.mania

import android.app.Application
import com.stats.mania.di.appModule
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Load Koin modules
            modules(appModule)
        }
    }
}