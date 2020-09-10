package com.amro.venuefinder.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.amro.venuefinder.R
import com.amro.venuefinder.data.Photos
import com.bumptech.glide.Glide

class SlidingImageAdapter(private val photos: Photos) :
    RecyclerView.Adapter<SlidingImageAdapter.ViewHolder>() {

    private val items = photos.groups?.get(0)?.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        val url = item?.let { it.prefix + it.width + "x" + it.height + it.suffix }

        Glide.with(holder.itemView)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById(R.id.image_item) as ImageView
    }
}