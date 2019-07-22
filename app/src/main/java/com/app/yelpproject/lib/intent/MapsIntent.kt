package com.app.yelpproject.lib.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.app.yelpproject.lib.toast.showToast
import com.google.android.gms.maps.model.LatLng

object MapsIntent {
    fun location(context: Context, latLng: LatLng) {
        open(context, "geo:${latLng.latitude},${latLng.longitude}")
    }
    fun loadWithMarker(context: Context, latLng: LatLng, label: String) {
        open(context,"geo:<${latLng.latitude}>,<${latLng.longitude}>?q=<${latLng.latitude}>,<${latLng.longitude}>($label)")
    }
    fun directions(context:Context,latLng:LatLng){
        open(context,"google.navigation:q=${latLng.latitude},${latLng.longitude}")
    }

    private fun open(context: Context, uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.`package` = PACKAGE
        if (intent.resolveActivity(context.packageManager) != null)
            context.startActivity(intent)
        else {
            context.showToast("Application not Existing!")
            PlaystoreIntent.openApplication(context, PACKAGE)
        }
    }


    private const val PACKAGE = "com.google.android.apps.maps"
}