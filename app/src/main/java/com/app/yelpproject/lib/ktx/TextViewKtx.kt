package com.app.yelpproject.lib.ktx

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.setTextColorRes(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}
fun TextView.setTextAndColor(text:String,@ColorRes color:Int){
    this.setTextColorRes(color)
    this.text = text
}
