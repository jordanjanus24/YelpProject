package com.app.yelpproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.yelpproject.R
import com.app.yelpproject.lib.ktx.loadImage
import kotlinx.android.synthetic.main.photo_item.view.*

class PhotosAdapter(private val photosList: ArrayList<String>) : RecyclerView.Adapter<PhotosAdapter.PhotosHolder>() {
    override fun getItemCount() = photosList.size
    override fun onBindViewHolder(holder: PhotosHolder, position: Int) = holder.imgPhoto.loadImage(photosList[position])
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotosHolder(itemView)
    }

    class PhotosHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto = view.imgPhoto!!
    }

}