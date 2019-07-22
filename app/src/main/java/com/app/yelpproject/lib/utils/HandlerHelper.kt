package com.app.yelpproject.lib.utils

import android.app.Activity
import android.os.Handler

fun delayedAction(delay: Long, runnable: Runnable) = HandlerHelper().createDelayedInterval(delay, runnable)
fun delayedAction(delay: Long, action: () -> Unit) =
    delayedAction(delay, Runnable { action.invoke() })

class HandlerHelper(activity: Activity? = null) {
    var handler: Handler? = null
    fun createDelayedInterval(delay: Long, runnable: Runnable): HandlerHelper {
        cancelCallbacks()
        handler = Handler()
        handler!!.postDelayed(runnable, delay)
        return this
    }

    fun cancelCallbacks() {
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)
        }
    }

}