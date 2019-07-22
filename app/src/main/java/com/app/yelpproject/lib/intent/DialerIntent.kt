package com.app.yelpproject.lib.intent

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresPermission
import com.app.yelpproject.lib.toast.showToast


object DialerIntent {
    fun open(context: Context, contactNumber: String) =
        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$contactNumber")))

    @RequiresPermission(Manifest.permission.CALL_PHONE)
    @Throws(SecurityException::class)
    fun call(context: Context, contactNumber: String, errorMessage: String = "There are no permission to Call.") =
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:$contactNumber")
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
            context.showToast(errorMessage)
        }

}