package com.app.yelpproject.rep

import android.util.Log
import com.app.yelpproject.yelp.YelpFusion
import com.yelp.fusion.client.models.Business
import com.yelp.fusion.client.models.Review

class BusinessRepository {
    companion object {
        val instance by lazy { BusinessRepository() }
    }

    val yelpFusionApi = YelpFusion.instance
    fun getBusinessList(params: HashMap<String, String>): List<Business> {
        val call = yelpFusionApi.getBusinessSearch(params)
        val res = call.execute()
        Log.d("Response", res.raw().body().toString())
        return res.body().businesses
    }

    fun getBusiness(id: String): Business {
        val call = yelpFusionApi.getBusiness(id)
        val res = call.execute()
        Log.d("Response", res.raw().body().toString())
        return res.body()
    }

    fun getReviews(id: String): List<Review> {
        val call = yelpFusionApi.getBusinessReviews(id, "en_US")
        val res = call.execute()
        Log.d("Response", res.raw().body().toString())
        return res.body().reviews
    }

}