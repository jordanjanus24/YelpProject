package com.app.yelpproject.lib.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.hideKeyboard() = KeyboardUtil(context).closeKeyboard(this)

class KeyboardUtil(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    fun showAsFocused(editText: EditText) {
        editText.requestFocus()
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }

    fun closeKeyboard(editText: EditText) {
        inputManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}