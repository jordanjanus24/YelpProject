package com.app.yelpproject.lib.ktx

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(path: String? = null) {
    try {
        if (path != null || path!!.isNotEmpty()) {
            Picasso.get().load(path).noPlaceholder().into(this)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}
