package dev.vanilson.popularmovies.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.data.network.APIService

class MoviesViewModel() : ViewModel() {

    private val movieService = APIService()
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies(sortMode: String?) {
        movieService.getMovies(sortMode, movies)
    }
}
