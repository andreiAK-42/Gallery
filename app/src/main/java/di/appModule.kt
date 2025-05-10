package di

import activity.myApp
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import database.appDao
import database.GalleryDatabase
import javax.inject.Singleton

@Module
class appModule(val application: Application) {
    @Singleton
    @Provides
    fun getNoteDao(noteDatabase: GalleryDatabase): appDao {
        return noteDatabase.getAppDao()
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): GalleryDatabase {
        return GalleryDatabase.getGalleryDatabaseInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}