package com.app.yelpproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.yelpproject.R
import com.app.yelpproject.views.list.BusinessView
import com.yelp.fusion.client.models.Business

class BusinessAdapter(
    private var businessList: List<Business>,
    private val onClick: ((View, Business) -> Unit)? = null
) :
    RecyclerView.Adapter<BusinessAdapter.BusinessHolder>() {

    fun setItems(businessList: List<Business>) {
        this.businessList = businessList
        this.notifyDataSetChanged()
    }

    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.business_item, parent, false)
        return BusinessHolder(itemView)
    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BusinessHolder, position: Int) {
        businessList[position].let {
            BusinessView.create(holder.rootView, it, position)
            holder.rootView.setOnClickListener { view ->
                onClick?.invoke(view, it)
            }
        }
    }

    class BusinessHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView = view.rootView!!
    }

}