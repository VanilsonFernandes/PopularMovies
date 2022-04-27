package dev.vanilson.popularmovies.io

import dev.vanilson.popularmovies.model.Movie
import dev.vanilson.popularmovies.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET("{sortMode}")
    fun getMovies(@Path("sortMode") sortMode: String, @Query("api_key") apiKey: String): Call<MovieResponse>
}