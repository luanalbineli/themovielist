package com.themovielist

import android.app.Application
import com.themovielist.di.AppComponent
import com.themovielist.di.DaggerAppComponent
import com.themovielist.di.DaggerComponentProvider
import timber.log.Timber

class MainApplication : Application(), DaggerComponentProvider {
    override var component: AppComponent = DaggerAppComponent.builder()
            .applicationContext(this)
            .build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}