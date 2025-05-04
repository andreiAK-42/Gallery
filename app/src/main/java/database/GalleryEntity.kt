package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery_entity")
class GalleryEntity(
    @PrimaryKey(autoGenerate = false)@ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "description") var description: String
)