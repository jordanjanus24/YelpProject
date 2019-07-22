package com.app.yelpproject.lib.animator.fade

import android.view.View
import androidx.core.view.isVisible
import com.app.yelpproject.lib.animator.animateOfFloatProperty

fun View.fadeIn(duration: Long = 1000) = this.apply {
    alpha = 0f
    animateOfFloatProperty("alpha", 1.0f, duration)
    isVisible = true
}

fun View.fadeOut(duration: Long = 1000) =
    animateOfFloatProperty("alpha", 0f, duration)