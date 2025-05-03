package activity

import activity.ui.mainAdapter
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import database.GalleryEntity
import viewModel.mainViewModel
import java.io.File

class MainActivity : AppCompatActivity(), mainAdapter.OnGalleryAdapterListener {
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 2
    lateinit var viewModel: mainViewModel
    lateinit var mainAdapter: mainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initAdapters()

        findViewById<Button>(R.id.addPictureButton).setOnClickListener{
            selectImageInAlbum()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(mainViewModel::class.java)
        viewModel.allPictureList.observe(this) { pictures ->
            mainAdapter.updateList(pictures)
        }
    }

    private fun initAdapters() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        mainAdapter = mainAdapter(viewModel.allPictureList.value ?: mutableListOf(), listener = this)
        recyclerView.adapter = mainAdapter
    }

    override fun onNoteDelete(note: GalleryEntity) {
        viewModel.deleteRecord(note)
    }

    override fun onNoteUpdate(note: GalleryEntity) {
        viewModel.updateRecord(note)
    }

    override fun onViewNote(note: GalleryEntity) {
       // openNote(note)
    }


    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                println("Выбранная папка: $uri")

                viewModel.insertRecord(GalleryEntity(path = uri.toString(), description = ""))
                   // val inputStream = contentResolver.openInputStream(uri)
                /*val imgBitmap = BitmapFactory.decodeStream(inputStream)
                findViewById<ImageView>(R.id.picture).setImageBitmap(imgBitmap)*/

            }
        }
    }

}