package com.techdivine.school

import android.app.Application
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class App : Application() {

    private var defaultSubscribeScheduler: Scheduler? = null

    companion object {
        var instance:App? = null
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun defaultSubscribeScheduler(): Scheduler? {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io()
        }
        return defaultSubscribeScheduler
    }

}