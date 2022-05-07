package dev.vanilson.popularmovies.data.repository

import dev.vanilson.popularmovies.data.model.Review
import dev.vanilson.popularmovies.data.network.ReviewService

class ReviewRepository {
    private val api = ReviewService()

    suspend fun getReviews(movieId: Int): List<Review> {
        return api.getReviews(movieId);
    }
}