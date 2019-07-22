package com.app.yelpproject.lib.ktx

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.app.yelpproject.lib.utils.KeyboardUtil

fun EditText.textString() = this.text.toString()
fun EditText.isTextNotEmpty() = this.textString().isNotEmpty()

fun EditText.addTextWatcher(afterTextChanged: (String) -> Unit): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }
    this.addTextChangedListener(textWatcher)
    return textWatcher
}
fun EditText.showAsFocused(context: Context) {
    KeyboardUtil(context).showAsFocused(this)
}
fun EditText.focusEditText() {
    KeyboardUtil(context).showAsFocused(this)
}