package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.main

import android.app.Application
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}