package com.app.yelpproject.lib.dialogs

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.app.yelpproject.lib.dialogs.button.DialogButton

class AlertDialogObject {

    companion object {
        fun create(
            activity: Activity,
            title: String? = null,
            message: String,
            positive: DialogButton? = null,
            negative: DialogButton? = null
        ) = AlertDialog.Builder(activity).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
            if (positive != null) {
                setPositiveButton(positive.title, positive.onClickListener)
            }
            if (negative != null) {
                setNegativeButton(negative.title, negative.onClickListener)
            }
        }.create().show()
    }
}