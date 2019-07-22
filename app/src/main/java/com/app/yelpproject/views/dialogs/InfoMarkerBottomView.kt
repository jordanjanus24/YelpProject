package com.app.yelpproject.views.dialogs

import android.app.Activity
import android.view.View
import com.app.yelpproject.R
import com.app.yelpproject.lib.bottomsheet.dialog.createBottomSheet
import com.app.yelpproject.lib.bottomsheet.dialog.setBackgroundColor
import com.app.yelpproject.lib.utils.inflater.createLayoutFromRes
import com.app.yelpproject.views.list.BusinessView
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.show_business_by_marker.view.*

object InfoMarkerBottomView {
    fun show(activity: Activity, business: Business, callBack: (View, Business) -> Unit) {
        activity.createBottomSheet(activity.createLayoutFromRes(R.layout.show_business_by_marker).apply {
            markerShowDetails.setOnClickListener {
                callBack.invoke(this, business)
            }
            BusinessView.create(this, business)
        }).setBackgroundColor(android.R.color.transparent).show()
    }
}