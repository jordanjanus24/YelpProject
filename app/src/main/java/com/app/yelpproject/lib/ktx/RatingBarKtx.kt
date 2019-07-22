package com.app.yelpproject.lib.ktx

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.widget.RatingBar
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

@SuppressLint("ResourceType")
fun RatingBar.setStarsForeground(@ColorRes foreground: Int) {
    val stars = this.progressDrawable as LayerDrawable
    RatingBarUtil.setRatingStarColor(stars.getDrawable(2), ContextCompat.getColor(context, foreground))
}

object RatingBarUtil {
    fun setRatingStarColor(drawable: Drawable, @ColorInt color: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) DrawableCompat.setTint(drawable, color)
        else drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
}