package com.app.yelpproject.lib.utils.inflater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.getInflater() = LayoutInflaterUtil(this, this.window.decorView.rootView as ViewGroup)

fun FragmentActivity.createLayoutFromRes(@LayoutRes resId: Int) = getInflater().createView(resId)

class LayoutInflaterUtil(
    activity: FragmentActivity,
    private val rootView: ViewGroup,
    private val inflater: LayoutInflater = activity.layoutInflater
) {
    fun createView(@LayoutRes resId: Int): View = inflater.inflate(resId, rootView, false)
}