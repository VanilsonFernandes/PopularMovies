package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.TrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrailerApiClient {
    @GET("{movieId}/videos")
    suspend fun getTrailers(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TrailerResponse>
}