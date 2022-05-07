package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.MovieResponse
import dev.vanilson.popularmovies.data.model.ReviewResponse
import dev.vanilson.popularmovies.data.model.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {
    @GET("{sortMode}")
    fun getMovies(
        @Path("sortMode") sortMode: String,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>
}