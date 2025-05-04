package activity

import activity.ui.mainAdapter
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import database.GalleryEntity
import viewModel.mainViewModel

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

    override fun onUpdateDescription(picture: GalleryEntity) {
        showDescriptionDialog(picture, this)
    }

    fun showDescriptionDialog(picture: GalleryEntity, context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.description_dialog, null)
        val descriptionValue = dialogView.findViewById<EditText>(R.id.et_description)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
            picture.description = descriptionValue.getText().toString()
            viewModel.updateRecord(picture)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onNoteUpdate(note: GalleryEntity) {
        viewModel.updateRecord(note)
    }

    override fun onViewNote(note: GalleryEntity) {
       // openNote(note)
    }


    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
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