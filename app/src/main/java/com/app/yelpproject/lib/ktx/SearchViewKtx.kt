package com.app.yelpproject.lib.ktx

import android.graphics.Color
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView

fun SearchView.removeUnderline() {
    this.findViewById<View>(androidx.appcompat.R.id.search_plate)?.setBackgroundColor(Color.TRANSPARENT)
}

fun MenuItem.getSearchView(
    queryHint: String,
    isIconified: Boolean? = false,
    isUnderlined: Boolean? = true
): SearchView {
    val searchView = this.actionView as SearchView
    searchView.queryHint = queryHint
    searchView.isIconified = isIconified!!
    if (!isUnderlined!!) searchView.removeUnderline()
    return searchView
}



fun SearchView.setQueryTextChangeListener(
    queryChange: ((String) -> Unit)
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean =
            true

        override fun onQueryTextChange(newText: String?): Boolean {
            queryChange.invoke(newText!!)
            return true
        }
    })
}

