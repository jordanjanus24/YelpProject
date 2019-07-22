package com.app.yelpproject.views

import android.annotation.SuppressLint
import android.view.View
import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.lib.ktx.loadImage
import com.app.yelpproject.lib.ktx.setStarsForeground
import com.app.yelpproject.lib.utils.StringUtil
import com.app.yelpproject.lib.utils.commaSeparatedArray
import com.app.yelpproject.lib.utils.delimeterByThousands
import com.app.yelpproject.lib.utils.reassignToArrayString
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.content_details.view.*
import kotlinx.android.synthetic.main.activity_details_toolbar.view.*

object DetailsView {
    @SuppressLint("SetTextI18n")
    fun create(view: View, business: Business) {
        view.imgProfile.loadImage(business.imageUrl)
        view.txtTitle.text = business.name
        view.txtCategories.text = business.categories.reassignToArrayString { it.title }.commaSeparatedArray()
        view.businessRating.rating = business.rating.toFloat()
        view.businessRating.setStarsForeground(android.R.color.white)
        view.txtReviews.text = "${business.reviewCount.delimeterByThousands()} reviews"
        view.txtPrice.text = business.price
        view.txtContactNumber.text = business.displayPhone
        view.txtAddrBusinessName.text = business.name
        view.txtAddress.text = BusinessConstants.displayAddress(business.location)
        view.reviewRatings.rating = business.rating.toFloat()
        view.txtReviewRatings.text = business.rating.toFloat().toString()
        view.txtReviewCount.text = "${business.reviewCount.delimeterByThousands()} reviews"
        view.txtDistance.text = StringUtil.formatWithSuffix(business.distance)
    }
}