package dev.vanilson.popularmovies.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.vanilson.popularmovies.model.Movie
import dev.vanilson.popularmovies.services.APIService

class MoviesViewModel() : ViewModel() {

    private val movieService = APIService()
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun updateMovies(sortMode: String?) {
        movieService.getMovies(sortMode, movies)
    }
}
