package com.app.yelpproject.views.list

import android.annotation.SuppressLint
import android.view.View
import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.lib.ktx.loadImage
import com.app.yelpproject.lib.utils.StringUtil
import com.app.yelpproject.lib.utils.commaSeparatedArray
import com.app.yelpproject.lib.utils.delimeterByThousands
import com.app.yelpproject.lib.utils.reassignToArrayString
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.business_item.view.*

object BusinessView {
    @SuppressLint("SetTextI18n")
    fun create(view: View, it: Business, position: Int? = null) {
        view.imgIcon.loadImage(it.imageUrl)
        if (position != null) view.txtBusinessName.text = "${position + 1}. " + it.name
        else view.txtBusinessName.text = "" + it.name
        view.txtAddress.text = BusinessConstants.displayAddress(it.location)
        view.rating.rating = it.rating.toFloat()
        view.txtPrice.text = it.price
        view.txtReviewsCount.text = "${it.reviewCount.delimeterByThousands()} Reviews"
        view.txtCuisineType.text = it.categories.reassignToArrayString { it.title }.commaSeparatedArray()
        view.txtDistance.text = StringUtil.formatWithSuffix(it.distance)
    }
}