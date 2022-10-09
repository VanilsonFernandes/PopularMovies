package dev.vanilson.popularmovies.domain

import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.data.repository.MovieRepository

class MovieUseCase {

    private val repository = MovieRepository()
//    suspend operator fun invoke(sortMode: String): List<Movie> = repository.getMovies(sortMode)

    suspend fun getMovies(sortMode: String): List<Movie> = repository.getMovies(sortMode)
    fun isFavorite(database: AppDatabase, movieId: Int): Boolean =
        repository.isFavorite(database, movieId)
    fun addToFavorite(database: AppDatabase, movie: Movie) = repository.addToFavorites(database, movie);
    fun getFavorites(database: AppDatabase): List<Movie> = repository.getFavorites(database)
}