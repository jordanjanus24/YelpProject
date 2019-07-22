package com.app.yelpproject.lib.toast

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.showToast(text: String, duration: Int? = Toast.LENGTH_LONG) = Toast(this, text, duration)
fun Activity.showToast(text: String, duration: Int? = Toast.LENGTH_LONG) = applicationContext.showToast(text, duration)
fun Fragment.showToast(text: String, duration: Int? = Toast.LENGTH_LONG) = activity!!.showToast(text, duration)

class Toast(
    context: Context,
    text: String = "",
    duration: Int? = Toast.LENGTH_LONG
) {
    init {
        Toast.makeText(context, text, duration!!).show()
    }
}