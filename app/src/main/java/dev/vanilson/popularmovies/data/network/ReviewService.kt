package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.Review
import dev.vanilson.popularmovies.data.model.ReviewResponse
import dev.vanilson.popularmovies.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ReviewService {

    private val retrofit = NetworkService.getRetrofitInstance(Constants.MOVIES_URL)

    suspend fun getReviews(movieId: Int): List<Review> {
        return withContext(Dispatchers.IO) {
            var response: Response<ReviewResponse>? = null

            try {
                response = retrofit.create(ReviewApiClient::class.java)
                    .getReviews(movieId, Constants.API_KEY)
            } catch (e: Exception) {
                println("getReviews.error: $e")
            }
            response?.body()?.results ?: emptyList()
        }
    }
}