package com.app.yelpproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.yelpproject.R
import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.lib.ktx.loadImage
import com.app.yelpproject.lib.utils.*
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.business_item.view.*
import kotlinx.android.synthetic.main.cuisine_item.view.*

class CuisineOptionsAdapter(
    private var optionsList: List<String>,
    private var existingList: ArrayList<String>,
    private val onClick: ((ArrayList<String>) -> Unit)? = null
) :
    RecyclerView.Adapter<CuisineOptionsAdapter.CuisineOptionsHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineOptionsHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cuisine_item, parent, false)
        return CuisineOptionsHolder(itemView)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CuisineOptionsHolder, position: Int) {
        with(holder) {
            txtCuisine.text = optionsList[position]
            chkCuisine.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!existingList.contains(optionsList[position]))
                        existingList.add(optionsList[position])
                } else existingList.remove(optionsList[position])
                onClick?.invoke(existingList)
            }
            rootView.setOnClickListener {
                chkCuisine.isChecked = !chkCuisine.isChecked
            }
            chkCuisine.isChecked = (existingList.contains(optionsList[position]))
        }
    }

    class CuisineOptionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView = view.rootView
        val chkCuisine = view.chkCuisine!!
        val txtCuisine = view.txtCuisine!!
    }

}