package com.app.yelpproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.yelpproject.R
import com.app.yelpproject.lib.intent.WebIntent
import com.app.yelpproject.lib.ktx.loadImage
import com.app.yelpproject.lib.ktx.onClickListener
import com.yelp.fusion.client.models.Review
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewsAdapter(private val reviewsList: List<Review>) : RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>() {


    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        with(holder) {
            reviewsList[position].let {
                imgIcon.loadImage(it.user.imageUrl)
                txtReviewer.text = it.user.name
                txtDescription.text = it.text
                rating.rating = it.rating.toFloat()
                rootView.onClickListener {
                    WebIntent.open(context, it.url)
                }
            }
        }
    }

    class ReviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView = view.rootView!!
        val imgIcon = view.imgIcon!!
        val txtReviewer = view.txtReviewer!!
        val rating = view.rating!!
        val txtDescription = view.txtReviewDescription!!
    }


}