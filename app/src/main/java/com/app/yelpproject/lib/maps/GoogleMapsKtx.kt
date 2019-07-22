package com.app.yelpproject.lib.maps

import android.util.Log
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

fun GoogleMap.zoomCoordinates(latLng: LatLng, zoomValue: Float = 13f) {
    try {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomValue)
        this.moveCamera(cameraUpdate)
        this.animateCamera(cameraUpdate, 5000, null)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.d("GoogleMap", "Retrying to Navigate Again!")
    }
}

fun GoogleMap.newMarker(
    coordinates: LatLng,
    title: String,
    snippet: String? = null
): Marker {
    val markerOpt = MarkerOptions().position(coordinates).title(title)
    if (snippet != null) markerOpt.snippet(snippet)
    return this.addMarker(markerOpt)
}

fun GoogleMap.onInfoWindowTag(callBack: (String) -> Unit) {
    this.setOnInfoWindowClickListener {
        callBack.invoke(it.tag.toString())
    }
}
