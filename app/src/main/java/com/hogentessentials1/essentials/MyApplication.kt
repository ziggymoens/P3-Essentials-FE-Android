package com.hogentessentials1.essentials

import android.app.Application
import com.hogentessentials1.essentials.data.model.DI.networkModule
import com.hogentessentials1.essentials.data.model.DI.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

import org.koin.core.context.startKoin

import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                networkModule,
                viewModelModule
            )
        }

    }
}