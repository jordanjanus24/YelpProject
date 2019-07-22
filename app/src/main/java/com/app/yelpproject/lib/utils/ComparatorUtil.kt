package com.app.yelpproject.lib.utils

fun <T> Comparator<T>?.sortWith(selector: (T) -> Comparable<*>, descending: Boolean? = false): Comparator<T>? {
    var sortedList: Comparator<T>? = this
    if (sortedList == null) {
        sortedList = if (!descending!!) compareBy(selector)
        else compareByDescending(selector)
    } else {
        if (!descending!!) sortedList.thenBy(selector)
        else sortedList.thenByDescending(selector)
    }
    return sortedList
}