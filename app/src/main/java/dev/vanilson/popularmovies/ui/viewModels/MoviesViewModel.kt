package dev.vanilson.popularmovies.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.domain.MovieUseCase
import dev.vanilson.popularmovies.utils.Constants
import kotlinx.coroutines.launch

class MoviesViewModel() : ViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    var movieUseCase = MovieUseCase()

    fun getMovies(sortMode: String?) {
        viewModelScope.launch {
            val result = movieUseCase(sortMode ?: Constants.POPULAR_SORTING)
            movies.postValue(result)
        }
    }
}
