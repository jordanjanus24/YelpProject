package com.app.yelpproject.lib.ktx

import android.os.Build
import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.FragmentActivity

inline fun FragmentActivity.setEnterSharedElementCompleteListener(
    crossinline onEnd: () -> Unit = {}
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                onEnd.invoke()
            }
        })
    }
}