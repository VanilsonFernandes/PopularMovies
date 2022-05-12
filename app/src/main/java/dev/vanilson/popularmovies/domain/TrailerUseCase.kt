package dev.vanilson.popularmovies.domain

import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.data.repository.TrailerRepository

class TrailerUseCase {

    private val repository = TrailerRepository()
    suspend operator fun invoke(movieId: Int): List<Trailer> = repository.getTrailers(movieId)
}