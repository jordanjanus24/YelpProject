package com.app.yelpproject.lib.ktx

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes

fun Menu.getMenuItem(@IdRes id: Int, visibility: Boolean? = true) =
    this.findItem(id).apply { this.isVisible = visibility!! }!!


fun MenuItem.onActionExpandCollapseListener(collapseActionView: () -> Unit, expandActionView: (() -> Unit)? = null) {
    this.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            expandActionView?.invoke()
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            collapseActionView.invoke()
            return true
        }

    })
}

fun MenuItem.expand() {this.expandActionView()}
fun MenuItem.collapse() {this.collapseActionView()}