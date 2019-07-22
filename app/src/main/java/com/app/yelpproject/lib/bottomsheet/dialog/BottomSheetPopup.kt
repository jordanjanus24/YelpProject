package com.app.yelpproject.lib.bottomsheet.dialog

import android.app.Activity
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.app.yelpproject.R
import com.app.yelpproject.lib.utils.getColorFromRes
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Activity.createBottomSheet(view: View?) = BottomSheetPopup.create(this, view)

fun BottomSheetDialog.setBackgroundColor(@ColorRes backgroundColor: Int? = android.R.color.transparent): BottomSheetDialog {
    this.window?.findViewById<View>(R.id.design_bottom_sheet)
        ?.setBackgroundColor(context.getColorFromRes(backgroundColor!!))
    return this
}

object BottomSheetPopup {

    fun create(
        activity: Activity,
        view: View?
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(activity)
        dialog.setContentView(view!!)
        return dialog
    }

}