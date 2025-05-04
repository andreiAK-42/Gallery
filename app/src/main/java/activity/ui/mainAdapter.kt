package activity.ui

import activity.MainActivity
import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.R
import database.GalleryEntity
import java.io.File
import java.net.URI

class mainAdapter(
    private var pictureList: MutableList<GalleryEntity>,
    private val listener: MainActivity
) :
    RecyclerView.Adapter<mainAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.rview_image)
        val description: TextView = itemView.findViewById(R.id.rview_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rview_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = pictureList[position]
        holder.description.text = picture.description
        val inputStream = holder.itemView.context.contentResolver.openInputStream(Uri.parse(picture.path))
        val bitmap = BitmapFactory.decodeStream(inputStream)
        Glide.with(holder.itemView.context)
            .load(bitmap)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)

        holder.image.setOnLongClickListener {
            listener.onUpdateDescription(picture)
            true
        }

    }

    fun deleteItem(position: Int) {
        listener.onNoteDelete(pictureList[position])
        pictureList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, pictureList.size)
    }

    fun updateList(newList: List<GalleryEntity>) {
        pictureList = newList.toMutableList()
        notifyDataSetChanged()
    }

    interface OnGalleryAdapterListener {
        fun onNoteDelete(note: GalleryEntity)
        fun onNoteUpdate(note: GalleryEntity)
        fun onViewNote(note: GalleryEntity)
        fun onUpdateDescription(picture: GalleryEntity)
    }


    override fun getItemCount(): Int {
        return pictureList.size
    }
}