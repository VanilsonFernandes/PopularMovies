package dev.vanilson.popularmovies.io

import dev.vanilson.popularmovies.model.MovieResponse
import dev.vanilson.popularmovies.model.ReviewResponse
import dev.vanilson.popularmovies.model.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET("{sortMode}")
    fun getMovies(
        @Path("sortMode") sortMode: String,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("{movieId}/reviews")
    fun getReviews(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<ReviewResponse>

    @GET("{movieId}/videos")
    fun getTrailers(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<TrailerResponse>
}