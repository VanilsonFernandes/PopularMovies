package dev.vanilson.popularmovies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.vanilson.popularmovies.R
import dev.vanilson.popularmovies.data.model.Review


class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder?>() {
    private var reviews: List<Review>? = null
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapterViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(R.layout.review_list_item, parent, false)
        return ReviewsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsAdapterViewHolder, position: Int) {
        holder.mAuthor.text = reviews?.get(position)?.author;
        holder.mContent.text = reviews?.get(position)?.content;
    }

    override fun getItemCount(): Int {
        return reviews?.size ?: 0
    }

    fun getReviews(): List<Review>? {
        return reviews
    }

    fun setReviews(reviews: List<Review>?) {
        this.reviews = reviews
        notifyDataSetChanged()
    }

    inner class ReviewsAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mAuthor: TextView = view.findViewById(R.id.tv_review_author)
        var mContent: TextView = view.findViewById(R.id.tv_review_content)
    }
}