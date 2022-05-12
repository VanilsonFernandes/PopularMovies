package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {
    @GET("{sortMode}")
    suspend fun getMovies(
        @Path("sortMode") sortMode: String,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>
}