package com.app.yelpproject.lib.ktx

import com.google.android.material.button.MaterialButtonToggleGroup


fun MaterialButtonToggleGroup.setOnButtonCheckedReference(callBack: () -> Unit) {
    this.addOnButtonCheckedListener { _, isChecked, _ ->
        callBack.invoke()
    }
}
