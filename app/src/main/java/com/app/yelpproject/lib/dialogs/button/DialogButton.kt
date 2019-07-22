package com.app.yelpproject.lib.dialogs.button

import android.content.DialogInterface

class DialogButton(val title: String, callBack: ((DialogInterface) -> Unit)? = null) {
    val onClickListener = DialogInterface.OnClickListener { dialog, _ ->
        callBack?.invoke(dialog)
        dialog.cancel()
    }

}