package com.app.yelpproject.lib.animator.inflater

import android.app.Activity
import android.content.Context
import android.os.Build
import android.transition.TransitionInflater
import androidx.annotation.TransitionRes

fun Activity.getTransition(@TransitionRes transition: Int) =
    TransitionsInflaterUtil.getTransition(applicationContext, transition)


object TransitionsInflaterUtil {
    fun getTransition(context: Context, @TransitionRes transition: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            TransitionInflater.from(context).inflateTransition(transition)
        else null
}