package uk.ac.tees.mad.travelr

import android.app.Application

class MyApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
