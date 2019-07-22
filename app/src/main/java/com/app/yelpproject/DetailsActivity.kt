package com.app.yelpproject

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.core.transition.addListener
import androidx.core.transition.doOnEnd
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.yelpproject.adapter.PhotosAdapter
import com.app.yelpproject.adapter.ReviewsAdapter
import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.intent.IntentFactory
import com.app.yelpproject.lib.Permission
import com.app.yelpproject.lib.animator.execAnimation
import com.app.yelpproject.lib.animator.fade.fadeIn
import com.app.yelpproject.lib.animator.inflater.getTransitionSetFromRes
import com.app.yelpproject.lib.animator.reveal.CircularRevealAnimator
import com.app.yelpproject.lib.animator.reveal.getCenter
import com.app.yelpproject.lib.intent.DialerIntent
import com.app.yelpproject.lib.intent.MapsIntent
import com.app.yelpproject.lib.intent.WebIntent
import com.app.yelpproject.lib.ktx.*
import com.app.yelpproject.lib.maps.GoogleMapsAPI
import com.app.yelpproject.lib.maps.newMarker
import com.app.yelpproject.lib.maps.zoomCoordinates
import com.app.yelpproject.lib.utils.DateUtils
import com.app.yelpproject.lib.utils.militaryTimeTo12HrsFormat
import com.app.yelpproject.lib.utils.setLightStatusBarIcons
import com.app.yelpproject.view_model.BusinessViewModel
import com.app.yelpproject.views.DetailsView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_toolbar.*
import kotlinx.android.synthetic.main.content_details.*

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val businessVM by lazy { ViewModelProviders.of(this).get(BusinessViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init(IntentFactory.get(intent))
        setLightStatusBarIcons(true)
        addTransitionListeners()
    }

    private fun addTransitionListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.sharedElementEnterTransition = getTransitionSetFromRes(R.transition.transitions_arc)
            window.sharedElementExitTransition = getTransitionSetFromRes(R.transition.transitions_arc, 200)
            window.sharedElementReturnTransition = getTransitionSetFromRes(R.transition.transitions_arc, 200)
            setEnterSharedElementCompleteListener {
                Log.d("Animation", "Done")
                val center = imgProfilePlaceholder.getCenter()
                CircularRevealAnimator.execute(imgProfile, {
                    imgProfile.isVisible = true
                }, {
                    imgProfilePlaceholder.isVisible = false
                }, center.first.toInt(), center.second.toInt(), 0f, imgProfile.width.toFloat())
                detailsScroller.fadeIn(800)
            }
        } else {
            imgProfilePlaceholder.isVisible = false
            imgProfile.isVisible = true
        }
    }

    private var business: Business? = null
    @SuppressLint("SetTextI18n")
    private fun init(business: Business) {
        this.business = business
        progress.isVisible = false
        GoogleMapsAPI.createMap(this, R.id.map)
        DetailsView.create(rootLayout, business)
        coordinates = BusinessConstants.latLngCoordinates(business.coordinates)
        setCallbacks(business)
    }

    private var coordinates: LatLng? = null
    @SuppressLint("MissingPermission")
    private fun setCallbacks(business: Business) {
        contactNumber.onClickContextMetadata(DialerIntent::open).data = business.phone
        locationMaps.onClickListener { MapsIntent.loadWithMarker(context, coordinates!!, business.name!!) }
        callNow.contextOnClickListener {
            if (Permission.isExisting(it, Manifest.permission.CALL_PHONE)) {
                DialerIntent.call(it, business.phone)
            } else Permission.requestPermission(this, Manifest.permission.CALL_PHONE, CALL_PHONE)
        }
        getDirections.onClickListener { MapsIntent.directions(context, coordinates!!) }
        visitWebsite.onClickContextMetadata(WebIntent::open).data = business.url
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                DialerIntent.call(applicationContext, business?.phone!!)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onMapReady(maps: GoogleMap?) {
        maps?.newMarker(
            coordinates!!,
            business!!.name,
            BusinessConstants.displayAddress(business?.location!!)
        )
        maps?.zoomCoordinates(coordinates!!)
        businessVM.getBusinessById(business?.id!!)
        businessVM.progressVisible.observe(this, Observer {
            progress.isVisible = it
        })
        businessVM.businessView.observe(this, Observer {
            businessPhotos.loadAdapter(PhotosAdapter(it.photos), LinearLayoutManager.HORIZONTAL)
            if (it.hours != null) {
                val hoursCurrent = BusinessConstants.getOpenDay(it)
                txtOpenTimes.text =
                    "Opens in " + DateUtils.getDayOfWeekFromIndex(hoursCurrent.day) + " : " +
                            hoursCurrent.start.militaryTimeTo12HrsFormat(false) + " - " +
                            hoursCurrent.end.militaryTimeTo12HrsFormat(false)
                when {
                    !it.hours[0].isOpenNow -> txtIsOpened.setTextAndColor("Closed", R.color.closedColor)
                    else -> txtIsOpened.setTextAndColor("Open", R.color.openedColor)
                }
            } else {
                txtOpenTimes.text = "No Business Hours."
                txtIsOpened.text = ""
            }

        })
        businessVM.reviewsList.observe(this, Observer {
            if (it != null)
                businessReviews.loadAdapter(ReviewsAdapter(it), LinearLayoutManager.VERTICAL)
        })
    }

    override fun onBackPressed() =
        if (detailsScroller.scrollY != 0) {
            appbar.setExpanded(true)
            detailsScroller.smoothScrollYAnimate(0)
        } else super.onBackPressed()

    companion object {
        const val CALL_PHONE = 100
    }
}