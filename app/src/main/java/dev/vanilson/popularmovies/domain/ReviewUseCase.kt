package dev.vanilson.popularmovies.domain

import dev.vanilson.popularmovies.data.model.Review
import dev.vanilson.popularmovies.data.repository.ReviewRepository

class ReviewUseCase {

    private val repository = ReviewRepository()
    suspend operator fun invoke(movieId: Int): List<Review> = repository.getReviews(movieId)
}