package com.app.yelpproject.lib

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.yelpproject.lib.dialogs.AlertDialogObject
import com.app.yelpproject.lib.dialogs.button.DialogButton

object Permission {
    fun request(
        activity: Activity,
        permission: Array<String>,
        identifier: Int,
        rationale: (() -> Unit)? = null
    ): Boolean {
        return if (isExisting(activity.applicationContext, permission)
        ) {
            true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission[0])) {
                rationale?.invoke()
                if (rationale == null) {
                    AlertDialogObject.create(
                        activity, null, REQUIRED_PERMISSION,
                        DialogButton("Proceed") {
                            requestPermission(activity, permission, identifier)
                        },
                        DialogButton("Cancel")
                    )
                }
            } else {
                requestPermission(activity, permission, identifier)
            }
            false
        }
    }

    fun isExisting(context: Context, permission: Array<String>) =
        ContextCompat.checkSelfPermission(
            context
            , permission[0]
        ) == PackageManager.PERMISSION_GRANTED

    fun isExisting(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(
            context
            , permission
        ) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(
        activity: Activity, permission: Array<String>,
        identifier: Int
    ) {
        ActivityCompat.requestPermissions(
            activity,
            permission,
            identifier
        )
    }

    fun requestPermission(
        activity: Activity, permission: String,
        identifier: Int
    ) {
        requestPermission(
            activity,
            arrayOf(permission),
            identifier
        )
    }

    const val REQUIRED_PERMISSION = "This permission is required in order to run the app."
}