package com.app.yelpproject.lib.animator

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

fun executeFloatAnimator(
    start: Float,
    end: Float,
    onUpdate: (Float) -> Unit,
    duration: Long = 300,
    onStart: (() -> Unit)? = null,
    onEnd: ((Float) -> Unit)? = null
) {
    val valueAnimator = ValueAnimator.ofFloat(start, end)
    valueAnimator.addUpdateListener {
        val value = it.animatedValue as Float
        onUpdate.invoke(value)
        if (end == value) onEnd?.invoke(value)
    }
    onStart?.invoke()
    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
}