package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApiClient {
    @GET("{movieId}/reviews")
    suspend fun getReviews(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<ReviewResponse>
}