package com.app.yelpproject.lib.animator

import android.animation.ObjectAnimator
import android.view.View

fun View.animateOfIntProperty(propertyName: String, value: Int, duration: Long = 300) =
    ObjectAnimator.ofInt(this, propertyName, value).apply {
        this.duration = duration
    }.start()

fun View.animateOfFloatProperty(propertyName: String, toValue: Float, duration: Long = 1000) =
    ObjectAnimator.ofFloat(this, propertyName, toValue).apply {
        this.duration = duration
    }.start()