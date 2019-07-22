package com.app.yelpproject.lib.animator.reveal

import android.annotation.TargetApi
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd

fun View.getCenter(): Pair<Float, Float> {
    val cx = this.x + this.width / 2
    val cy = this.y + this.height / 2
    return Pair(cx, cy)
}

object CircularRevealAnimator {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun execute(
        view: View,
        onStartAnim: (() -> Unit)? = null,
        onEndAnimation: (() -> Unit)? = null,
        centerX: Int,
        centerY: Int,
        startRadius: Float,
        finalRadius: Float,
        duration: Long? = 400
    ) {
        val circularReveal =
            ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, finalRadius)
        circularReveal.duration = duration!!
        circularReveal.interpolator = AccelerateDecelerateInterpolator()
        circularReveal.addListener {
            it.doOnEnd {
                onEndAnimation?.invoke()
            }
        }
        onStartAnim?.invoke()
        circularReveal.start()
    }
}