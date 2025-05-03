package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface appDao {

    @Query("SELECT * FROM gallery_entity ORDER BY path DESC")
    fun getAllRecordsFromDB(): MutableList<GalleryEntity>?

    @Insert
    fun insertRecord(picture: GalleryEntity)

    @Delete
    fun deleteRecord(picture: GalleryEntity)

    @Update
    fun updateRecord(picture: GalleryEntity)
}