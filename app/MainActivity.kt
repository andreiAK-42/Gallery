package com.example.gallery.activity.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gallery.R

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_OPEN_DIRECTORY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openDirectoryPicker()
    }

    private fun openDirectoryPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            // Опционально: можно задать начальную директорию
            // flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivityForResult(intent, REQUEST_CODE_OPEN_DIRECTORY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_OPEN_DIRECTORY && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // uri содержит выбранную директорию
                // Теперь можно работать с файлами в этой папке
                println("Выбранная папка: $uri")

                // Для долгосрочного доступа нужно взять разрешение
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }
    }

    fun hasPermissions(context: Context, permissions: Array<String>, activity: MainActivity) {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity, permissions, 1
                )
            }
        }
    }



    var PERMISSIONS_LIST = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    companion object {
        private const val STORAGE_PERMISSION_CODE = 101
        private const val PICK_IMAGE_REQUEST = 102
    }

}