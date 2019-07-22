package com.app.yelpproject.views.dialogs

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.app.yelpproject.R
import com.app.yelpproject.lib.bottomsheet.dialog.createBottomSheet
import com.app.yelpproject.lib.ktx.addTextWatcher
import com.app.yelpproject.lib.ktx.contextOnClickListener
import com.app.yelpproject.lib.ktx.focusEditText
import com.app.yelpproject.lib.ktx.showAsFocused
import com.app.yelpproject.lib.utils.getColorFromRes
import com.app.yelpproject.lib.utils.inflater.createLayoutFromRes
import kotlinx.android.synthetic.main.search_by_address.view.*

object AddressFilterBottomView {
    fun show(activity: Activity, existingData: String, onFilter: (String) -> Unit) {
        var txtFilter: EditText? = null
        activity.createBottomSheet(activity.createLayoutFromRes(R.layout.search_by_address).apply {
            txtSearchAddressFilter.setText(existingData)
            searchFiltersAddress.contextOnClickListener(txtSearchAddressFilter::showAsFocused)
            txtSearchAddressFilter.addTextWatcher {
                onFilter.invoke(it)
            }
            txtFilter = txtSearchAddressFilter
        }).apply {
            txtFilter?.focusEditText()
            window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                    .or(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window?.statusBarColor = context.getColorFromRes(android.R.color.transparent)
                window?.navigationBarColor = context.getColorFromRes(android.R.color.transparent)
            }
        }.show()
    }
}