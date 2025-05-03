package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GalleryEntity::class], version = 1)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun getAppDao(): appDao

    companion object {
        private var db_instance: GalleryDatabase? = null

        fun getGalleryDatabaseInstance(context: Context): GalleryDatabase {
            if (db_instance == null) {
                db_instance = Room.databaseBuilder<GalleryDatabase>(
                    context.applicationContext, GalleryDatabase::class.java, "gallery_db"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return db_instance!!
        }
    }
}