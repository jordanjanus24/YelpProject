package com.app.yelpproject.constants

import com.app.yelpproject.lib.utils.DateUtils
import com.app.yelpproject.lib.utils.addArray
import com.app.yelpproject.lib.utils.groupList
import com.app.yelpproject.lib.utils.reassignToArrayString
import com.google.android.gms.maps.model.LatLng
import com.yelp.fusion.client.models.*

fun ArrayList<Category>.reassignToString() =
    this.reassignToArrayString { it.title }

object BusinessConstants {

    fun displayAddress(location: Location) =
        if (location.zipCode != null)
            location.address1 + ", " + location.city + " " + location.zipCode
        else
            location.address1 + ", " + location.city

    fun matchesAddressFields(location: Location, filter: String) =
        when {
            location.zipCode != null -> (location.address1.contains(filter, true)
                    || location.city.contains(filter, true)
                    || location.zipCode.contains(filter, true))
            else -> location.address1.contains(filter, true)
                    || location.city.contains(filter, true)
        }

    fun latLngCoordinates(coordinates: Coordinates) =
        LatLng(coordinates.latitude, coordinates.longitude)

    fun getCuisineOptions(list: List<Business>): List<String> {
        val options = ArrayList<String>()
        list.forEach { business -> options.addArray(business.categories.reassignToArrayString { it.title }) }
        options.groupList()
        options.sort()
        return options
    }

    fun getOpenDay(it: Business): Open = try {
        it.hours[0].open[DateUtils.getDayOfWeekFromCurrentDateIndex() - 1]
    } catch (ex: ArrayIndexOutOfBoundsException) {
        it.hours[0].open[0]
    }

}
