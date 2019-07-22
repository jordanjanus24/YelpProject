package com.app.yelpproject.lib.country

import com.app.yelpproject.lib.country.item.Country
import java.text.Collator
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

object Countries {
    fun getAll(excludeCountry: String? = null): ArrayList<Country> {
        val allCountries = ArrayList<Country>()
        val countries = Locale.getISOCountries()
        countries.forEach {
            val locale = Locale("en", it)
            if (locale.isO3Country != "" && locale.country != "" && locale.displayCountry != "") {
                if (excludeCountry != null) {
                    if (locale.country != excludeCountry || locale.displayCountry != excludeCountry) {
                        allCountries.add(Country(locale.isO3Country, locale.country, locale.displayCountry))
                    }
                } else {
                    allCountries.add(Country(locale.isO3Country, locale.country, locale.displayCountry))
                }
            }
        }
        Collections.sort(allCountries, CountryComparator())
        return allCountries
    }

    private class CountryComparator : Comparator<Country> {
        val comparator: Collator = Collator.getInstance()
        override fun compare(c1: Country?, c2: Country?): Int {
            return comparator.compare(c1?.name, c2?.name)
        }
    }
}