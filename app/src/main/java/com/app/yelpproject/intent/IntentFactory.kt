package com.app.yelpproject.intent

import android.content.Intent
import android.os.Bundle
import com.yelp.fusion.client.models.Business

object IntentFactory {

    fun set(intent: Intent, business: Business): Intent {
        val bundle = Bundle()
        bundle.putSerializable(BUSINESS, business)
        intent.putExtra(BUNDLE, bundle)
        return intent
    }

    fun get(intent: Intent): Business {
        val extras = intent.getBundleExtra(BUNDLE)
        return extras.getSerializable(BUSINESS) as Business
    }

    private const val BUNDLE = "bundle"
    private const val BUSINESS = "business"
}
