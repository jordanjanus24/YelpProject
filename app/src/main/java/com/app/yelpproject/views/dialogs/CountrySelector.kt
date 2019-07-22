package com.app.yelpproject.views.dialogs

import android.app.Activity
import com.app.yelpproject.R
import com.app.yelpproject.lib.bottomsheet.collapseSheet
import com.app.yelpproject.lib.bottomsheet.dialog.createBottomSheet
import com.app.yelpproject.lib.country.Countries
import com.app.yelpproject.lib.country.adapter.SimpleCountryListAdapter
import com.app.yelpproject.lib.ktx.addTextWatcher
import com.app.yelpproject.lib.ktx.contextOnClickListener
import com.app.yelpproject.lib.ktx.loadAdapter
import com.app.yelpproject.lib.ktx.showAsFocused
import com.app.yelpproject.lib.utils.inflater.createLayoutFromRes
import com.app.yelpproject.lib.utils.toArrayList
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.country_selector.view.*

object CountrySelector {
    private var countrySelector: BottomSheetDialog? = null
    fun show(activity: Activity, currentCountry: String, onSelectCountry: (String) -> Unit): Boolean {
        countrySelector = activity.createBottomSheet(activity.createLayoutFromRes(R.layout.country_selector).apply {
            val adapter = SimpleCountryListAdapter(Countries.getAll(currentCountry)) {
                countrySelector?.dismiss()
                onSelectCountry.invoke(it.code)
            }
            searchByCountry.contextOnClickListener(txtSearchCountry::showAsFocused)
            countryOptions.loadAdapter(adapter)
            txtSearchCountry.addTextWatcher { text ->
                val countries =
                    Countries.getAll(currentCountry).filter { it.name.contains(text, true) }
                adapter.loadItems(countries.toArrayList())
            }
        })
        countrySelector?.show()
        return true
    }
}