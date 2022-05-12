package dev.vanilson.popularmovies.data.repository

import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.data.network.TrailerService

class TrailerRepository {
    private val api = TrailerService()

    suspend fun getTrailers(movieId: Int): List<Trailer> {
        return api.getTrailers(movieId);
    }
}