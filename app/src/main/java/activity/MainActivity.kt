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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import database.GalleryEntity
import viewModel.mainViewModel

class MainActivity : AppCompatActivity() {
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 2
    lateinit var viewModel: mainViewModel
    lateinit var mainAdapter: mainAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* initViewModel()
        initAdapters()*/

       /* findViewById<Button>(R.id.addPictureButton).setOnClickListener{
            selectImageInAlbum()
        }*/

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        
       // setupActionBarWithNavController(navController)
    }



}