package com.hogentessentials1.essentials.data.model

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}