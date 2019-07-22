package com.app.yelpproject.lib.intent

import android.content.Context
import android.content.Intent
import android.net.Uri

object WebIntent {
    fun open(context: Context, url: String) =
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}