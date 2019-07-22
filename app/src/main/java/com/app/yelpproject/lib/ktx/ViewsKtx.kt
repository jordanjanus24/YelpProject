package com.app.yelpproject.lib.ktx

import android.content.Context
import android.os.Build
import android.view.View


fun <T : View> T.onClickListener(callBack: T.() -> Unit): T {
    this.setOnClickListener {
        callBack.invoke(this)
    }
    return this
}

fun <T : View> T.contextOnClickListener(callBack: (Context) -> Unit): T {
    this.setOnClickListener {
        callBack.invoke(this.context)
    }
    return this
}

fun View.setOnClickReference(callBack: () -> Unit) {
    this.setOnClickListener {
        callBack.invoke()
    }
}


fun <T : View> T.setViewElevation(elevation: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.elevation = elevation
    }
}

inline var View.data: String
    get() = tag.toString()
    set(value) {
        tag = value
    }

fun <T : View> T.onClickContextMetadata(callBack: (Context, String) -> Unit): T {
    this.setOnClickListener {
        callBack.invoke(context, data)
    }
    return this
}

fun <T : View> T.onClickMetadata(callBack: (String) -> Unit): T {
    this.setOnClickListener {
        callBack.invoke(data)
    }
    return this
}

fun View.getPair(): androidx.core.util.Pair<View, String>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        androidx.core.util.Pair(this, transitionName)
    } else {
        null
    }
}
