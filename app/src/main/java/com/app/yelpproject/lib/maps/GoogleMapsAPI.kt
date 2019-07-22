package com.app.yelpproject.lib.maps

import androidx.fragment.app.FragmentActivity
import com.app.yelpproject.lib.fragment.replaceFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

object GoogleMapsAPI {
    fun createMap(callback: FragmentActivity, id: Int): SupportMapFragment? {
        if (callback is OnMapReadyCallback) {
            val mapFragment = SupportMapFragment.newInstance()
            callback.replaceFragment(id, mapFragment)
            mapFragment.getMapAsync(callback)
            return mapFragment
        }
        return null
    }
}