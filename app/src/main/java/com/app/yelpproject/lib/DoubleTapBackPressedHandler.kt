package com.app.yelpproject.lib

import android.app.Activity
import com.app.yelpproject.lib.toast.showToast

class DoubleTapBackPressedHandler(
    private val context: Activity,
    private val interval: Long = 2000
) {
    private var backPressedInterval: Long = 0
    fun onTapBackPressed() {
        if (backPressedInterval + interval > System.currentTimeMillis())
            close()
        else {
            context.showToast(PRESS_AGAIN_TO_EXIT)
        }
        backPressedInterval = System.currentTimeMillis()
    }

    private fun close() {
        context.finish()
    }

    companion object {
        const val PRESS_AGAIN_TO_EXIT = "Press again to exit!"
    }
}