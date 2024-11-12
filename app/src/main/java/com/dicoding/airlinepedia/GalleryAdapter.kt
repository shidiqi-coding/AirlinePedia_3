package com.dicoding.airlinepedia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val context: Context,
    private val imageList: List<Int>,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.imageView.setImageResource(imageResId)

        // Handle click to show the image in full screen
        holder.itemView.setOnClickListener {
            onImageClick(imageResId)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_gallery_image)
    }
}