package com.app.yelpproject.lib.utils.inflater

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes

fun Context.getInflater() = ContextInflater(this)

fun Context.createLayoutFromRes(@LayoutRes resId: Int) = getInflater().createView(resId)

class ContextInflater(
    context: Context,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) {
    fun createView(@LayoutRes resId: Int) = inflater.inflate(resId, null, false)!!
}