package com.app.yelpproject.lib.utils

import com.app.yelpproject.lib.data.IndexedData
import java.lang.StringBuilder

fun <T : ArrayList<E>, E> T.reassignToArrayString(callBack: (E) -> String): ArrayList<String> {
    val arr = ArrayList<String>()
    this.forEach {
        arr.add(callBack.invoke(it))
    }
    return arr
}

fun ArrayList<String>.groupList() {
    val arr = ArrayList<String>()
    this.forEach {
        if (!arr.contains(it)) arr.add(it)
    }
    this.clear()
    this.addArray(arr)
}

fun <T : List<E>, E> T.findDataIndex(findThis: (E) -> Boolean): Int? {
    this.forEachIndexed { index, e ->
        if (findThis.invoke(e)) {
            return index
        }
    }
    return null
}

fun <T : List<E>, E> T.findData(findThis: (E) -> Boolean): E? {
    this.forEach {
        if (findThis.invoke(it)) {
            return it
        }
    }
    return null
}

fun <T : List<E>, E> T.findDataWithIndex(findThis: (E) -> Boolean): IndexedData<E>? {
    this.forEachIndexed { index, e ->
        if (findThis.invoke(e)) {
            return IndexedData(index, e)
        }
    }
    return null
}


fun <T : ArrayList<String>> T.addArray(arr: ArrayList<String>): ArrayList<String> {
    arr.forEach {
        this.add(it)
    }
    return this
}

fun <T : List<String>> T.toArrayString(): Array<String?> {
    val arr = arrayOfNulls<String>(this.size)
    this.forEachIndexed { index, s ->
        arr[index] = s
    }
    return arr
}

fun <T : List<E>, E> T.toArrayList(): ArrayList<E> {
    val arrayList = ArrayList<E>()
    this.forEach {
        arrayList.add(it)
    }
    return arrayList
}

fun List<String>.checkMatches(arr: List<String>): Int {
    var matches = 0
    this.forEach {
        if (arr.contains(it)) matches++
    }
    return matches
}

fun ArrayList<String>.commaSeparatedArray(separator: String? = ", ") = ArrayUtil.commaSeparatedArray(this, separator)

object ArrayUtil {
    fun commaSeparatedArray(arr: ArrayList<String>, separator: String? = ", "): String {
        val sb = StringBuilder()
        arr.forEachIndexed { index, s ->
            sb.append(s)
            if (index < arr.size - 1) sb.append(separator)
        }
        return sb.toString()
    }
}