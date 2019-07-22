package com.app.yelpproject.lib.utils

import android.content.Context
import android.location.Geocoder
import android.telephony.TelephonyManager
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception
import java.util.*

fun String.toDisplayCountry() = Locale("", this).displayCountry

object LocationUtil {
    fun getLatLngFromLocation(context: Context, city: String): LatLng? {
        val geo = Geocoder(context, context.resources.configuration.locale)
        var latlng: LatLng? = null
        try {
            val address = geo.getFromLocationName(city, 1)[0]
            latlng = LatLng(address.latitude, address.longitude)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return latlng
    }

    fun getCurrentCountryFromTelephony(context: Context): String? {
        var countryCode: String? = null
        try {
            val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = telephony.simCountryIso
            if (simCountry != null && simCountry.length == 2) {
                countryCode = simCountry.toUpperCase(Locale.US)
            } else if (telephony.phoneType != TelephonyManager.PHONE_TYPE_CDMA) {
                val networkCountry = telephony.networkCountryIso
                if (networkCountry != null && networkCountry.length == 2)
                    countryCode = networkCountry.toUpperCase(Locale.US)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return countryCode
    }
}