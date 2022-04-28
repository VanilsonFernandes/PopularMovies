package dev.vanilson.popularmovies.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.vanilson.popularmovies.model.Movie
import dev.vanilson.popularmovies.services.MovieService

class MoviesViewModel() : ViewModel() {

    private val movieService = MovieService()
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun updateMovies(sortMode: String?) {
        movieService.getMovies(sortMode, movies)
    }
}
