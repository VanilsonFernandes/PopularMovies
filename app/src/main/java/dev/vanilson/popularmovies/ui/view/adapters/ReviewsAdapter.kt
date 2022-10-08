package dev.vanilson.popularmovies.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.vanilson.popularmovies.data.model.Review
import dev.vanilson.popularmovies.databinding.ReviewListItemBinding


class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder?>() {
    private var reviews: List<Review>? = null
    private lateinit var mContext: Context
    private lateinit var binding: ReviewListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapterViewHolder {
        mContext = parent.context

        binding = ReviewListItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ReviewsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewsAdapterViewHolder, position: Int) {
        binding.tvReviewAuthor.text = reviews?.get(position)?.author;
        binding.tvReviewContent.text = reviews?.get(position)?.content;
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

    inner class ReviewsAdapterViewHolder(binding: ReviewListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}