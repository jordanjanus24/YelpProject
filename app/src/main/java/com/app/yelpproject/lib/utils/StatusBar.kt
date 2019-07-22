package com.app.yelpproject.lib.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window

fun Activity.setLightStatusBarIcons(light: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (light) window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility.xor(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        else window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}
