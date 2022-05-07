package dev.vanilson.popularmovies.domain

import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.data.repository.MovieRepository

class MovieUseCase {

    private val repository = MovieRepository()
    suspend operator fun invoke(sortMode: String): List<Movie> = repository.getMovies(sortMode)
}