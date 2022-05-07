package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrailerApiClient {
    @GET("{movieId}/videos")
    fun getTrailers(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<TrailerResponse>
}