package com.app.yelpproject.lib.ktx

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.loadAdapter(
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
    orientation: Int = RecyclerView.VERTICAL
) {
    this.layoutManager = LinearLayoutManager(context, orientation, false)
    this.itemAnimator = DefaultItemAnimator()
    this.adapter = adapter
}

fun RecyclerView.addDivider(
    @DrawableRes drawable: Int? = null, orientation: Int = DividerItemDecoration.VERTICAL
) {
    val decorator = DividerItemDecoration(context, orientation)
    DividerItemDecoration(context, orientation)
    if (drawable != null) {
        decorator.setDrawable(ContextCompat.getDrawable(context, drawable)!!)
    }
    addItemDecoration(decorator)
}