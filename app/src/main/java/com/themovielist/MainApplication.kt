package com.themovielist

import android.app.Application
import com.themovielist.di.AppComponent
import com.themovielist.di.DaggerAppComponent
import timber.log.Timber

class MainApplication: Application() {
    val component: AppComponent = DaggerAppComponent
            .builder()
            .applicationContext(this)
            .build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}