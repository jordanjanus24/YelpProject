package com.app.yelpproject.lib.utils

import android.app.Activity
import android.view.WindowManager

fun Activity.noLimits(noLimits: Boolean) =
    if (noLimits) window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    else window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)