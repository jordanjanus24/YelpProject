package com.app.yelpproject.lib.ktx

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

fun AutoCompleteTextView.setSuggestions(arr: List<String>) =
    setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arr))