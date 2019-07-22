package com.app.yelpproject.views.dialogs

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.yelpproject.R
import com.app.yelpproject.adapter.CuisineOptionsAdapter
import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.lib.bottomsheet.dialog.createBottomSheet
import com.app.yelpproject.lib.ktx.loadAdapter
import com.app.yelpproject.lib.utils.inflater.createLayoutFromRes
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.search_by_cuisine.view.*

object CuisineOptionsBottomView {
    fun show(
        activity: Activity,
        existingData: ArrayList<String>,
        baseList: List<Business>,
        onSelectCuisine: (ArrayList<String>) -> Unit
    ) {
        activity.createBottomSheet(activity.createLayoutFromRes(R.layout.search_by_cuisine).apply {
            cuisineOptions.loadAdapter(
                CuisineOptionsAdapter(
                    BusinessConstants.getCuisineOptions(baseList),
                    existingData
                ) { items ->
                    onSelectCuisine.invoke(items)
                }, LinearLayoutManager.VERTICAL
            )
        }).show()
    }
}