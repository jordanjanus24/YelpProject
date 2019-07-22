package com.app.yelpproject.constants.filters

import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.constants.reassignToString
import com.app.yelpproject.lib.utils.checkMatches
import com.yelp.fusion.client.models.Business

object BusinessFilter {
    fun apply(list: List<Business>, query: String, filterCuisine: ArrayList<String>, addressFilter: String) =
        list.filter { it.name.contains(query, true) }
            .filter { filterByCuisines(it, filterCuisine) }
            .filter { filterByAddress(it, addressFilter) }

    private fun filterByCuisines(it: Business, filterCuisine: ArrayList<String>): Boolean =
        when {
            filterCuisine.size >= 1 -> it.categories.reassignToString().checkMatches(filterCuisine) >= 1
            else -> true
        }

    private fun filterByAddress(it: Business, addressFilter: String) = when {
        addressFilter.isNotBlank() -> BusinessConstants.displayAddress(it.location).contains(addressFilter, true) ||
                BusinessConstants.matchesAddressFields(it.location, addressFilter)
        else -> true
    }
}