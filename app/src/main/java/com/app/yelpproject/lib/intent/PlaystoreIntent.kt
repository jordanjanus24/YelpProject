package com.app.yelpproject.lib.intent

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

object PlaystoreIntent {
    @Throws(ActivityNotFoundException::class)
    fun openApplication(context: Context, packageName: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            intent.data = Uri.parse("market://details?id=$packageName")
            context.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            ex.printStackTrace()
            WebIntent.open(context, "https://play.google.com/store/apps/details?id=$packageName")
        }
    }
}