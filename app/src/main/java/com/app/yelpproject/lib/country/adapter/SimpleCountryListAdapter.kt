package com.app.yelpproject.lib.country.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.yelpproject.R
import com.app.yelpproject.lib.country.Countries
import com.app.yelpproject.lib.country.item.Country
import kotlinx.android.synthetic.main.country_item.view.*

class SimpleCountryListAdapter(
    private var countries: ArrayList<Country> = Countries.getAll(),
    private val callBack: ((Country) -> Unit)? = null
) :
    RecyclerView.Adapter<SimpleCountryListAdapter.CountryDataHolder>() {

    fun loadItems(countries: ArrayList<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountryDataHolder, position: Int) {
        holder.country.text = countries[position].name
        holder.rootView.setOnClickListener {
            callBack?.invoke(countries[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDataHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryDataHolder(itemView)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class CountryDataHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView = view.rootView
        val country = view.txtCountrySelect
    }

}