package com.app.yelpproject.lib.ktx

import androidx.appcompat.widget.Toolbar


fun Toolbar.setOnNavigationClickReference(callBack: () -> Unit) {
    this.setNavigationOnClickListener {
        callBack.invoke()
    }
}
