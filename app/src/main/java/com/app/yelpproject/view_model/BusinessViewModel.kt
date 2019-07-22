package com.app.yelpproject.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.yelpproject.lib.OnlineTask
import com.app.yelpproject.yelp.YelpFusion
import com.app.yelpproject.lib.utils.toDisplayCountry
import com.app.yelpproject.rep.BusinessRepository
import com.yelp.fusion.client.models.Business
import com.yelp.fusion.client.models.Review

class BusinessViewModel : ViewModel() {
    val repository by lazy { BusinessRepository.instance }
    val businessList = MutableLiveData<List<Business>>()
    val progressVisible = MutableLiveData<Boolean>()
    val catchedException = MutableLiveData<Exception?>()

    val businessView = MutableLiveData<Business>()
    val reviewsList = MutableLiveData<List<Review>>()
    val currentCountry = MutableLiveData<String>()
    fun init(country: String? = null) {
        setProgressVisible(false)
        Log.d(javaClass.name, "Getting Data on Country : $country")
        getListByCountry(country!!)
    }

    fun getListByCountry(country: String = DEFAULT_COUNTRY) {
        catchedException.postValue(null)
        OnlineTask.create({
            val params = YelpFusion.createParams()
            params["location"] = country
            businessList.postValue(repository.getBusinessList(params))
        }, {
            setProgressVisible(it)
        }, {
            catchedException.postValue(it)
        }).execute()
        currentCountry.postValue(country.toDisplayCountry())
    }

    fun getBusinessById(businessId: String) {
        catchedException.postValue(null)
        OnlineTask.create({
            businessView.postValue(repository.getBusiness(businessId))
        }, {
            setProgressVisible(it)
            getReviewsByBusinessId(businessId)
        }, {
            catchedException.postValue(it)
        }).execute()
    }

    fun getReviewsByBusinessId(businessId: String) {
        catchedException.postValue(null)
        OnlineTask.create({
            reviewsList.postValue(repository.getReviews(businessId))
        }, {
            setProgressVisible(it)
        }, {
            catchedException.postValue(it)
        }).execute()
    }

    fun setProgressVisible(isVisible: Boolean) = progressVisible.postValue(isVisible)

    companion object {
        const val DEFAULT_COUNTRY = "Philippines"
    }
}