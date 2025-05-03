package activity

import android.app.Application
import di.AppComponent
import di.DaggerAppComponent

import di.appModule

class myApp : Application() {

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.builder()
            .appModule(appModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return _appComponent
    }
}