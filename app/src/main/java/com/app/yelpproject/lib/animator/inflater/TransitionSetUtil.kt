package com.app.yelpproject.lib.animator.inflater

import android.animation.TimeInterpolator
import android.app.Activity
import android.os.Build
import android.transition.Transition
import android.transition.TransitionSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.TransitionRes

fun Activity.getTransitionSetFromRes(
    @TransitionRes transition: Int, startDelay: Long? = 0, duration: Long? = 400,
    interpolator: TimeInterpolator? = AccelerateDecelerateInterpolator()
) = TransitionSetUtil.create(getTransition(transition)!!, startDelay, duration, interpolator)

object TransitionSetUtil {

    fun create(
        transition: Transition,
        startDelay: Long? = 0,
        duration: Long? = 400,
        interpolator: TimeInterpolator? = AccelerateDecelerateInterpolator()
    ) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) TransitionSet().apply {
            addTransition(transition)
            this.duration = duration!!
            this.interpolator = interpolator
            if (this.startDelay.toInt() != 0)
                this.startDelay = startDelay!!
        }
        else null

}