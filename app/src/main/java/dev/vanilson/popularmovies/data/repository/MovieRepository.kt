package dev.vanilson.popularmovies.data.repository

import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.data.network.MovieService

class MovieRepository {
    private val api = MovieService()

    suspend fun getMovies(sortMode: String): List<Movie> {
        return api.getMovies(sortMode);
    }

    fun isFavorite(database: AppDatabase, movieId: Int): Boolean {
        return database.movieDao().findById(movieId) != null
    }

    fun addToFavorites(database: AppDatabase, movie: Movie) {
        database.movieDao().insertAll(movie)
    }
}