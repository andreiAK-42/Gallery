package viewModel

import activity.myApp
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gallery.R
import database.appDao
import database.GalleryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class mainViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var appDao: appDao

    val allPictureList = MutableLiveData<MutableList<GalleryEntity>>(mutableListOf())
    init {
        (application as myApp).getAppComponent().inject(this)
        getAllRecords()
    }

    private fun getAllRecords() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = appDao.getAllRecordsFromDB() ?: mutableListOf()
            allPictureList.postValue(list)
        }
    }

    fun deleteRecord(noteEntity: GalleryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.deleteRecord(noteEntity)

            val updatedList = appDao.getAllRecordsFromDB() ?: mutableListOf()
            allPictureList.postValue(updatedList)
        }

    }

    fun updateRecord(noteEntity: GalleryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.updateRecord(noteEntity)
            getAllRecords()
        }
    }

    fun insertRecord(noteEntity: GalleryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.insertRecord(noteEntity)
            getAllRecords()
        }

    }
}