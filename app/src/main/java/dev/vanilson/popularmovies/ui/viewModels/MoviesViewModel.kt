package dev.vanilson.popularmovies.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.domain.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel() : ViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    var movieUseCase = MovieUseCase()

    fun getMovies(sortMode: String) {
        viewModelScope.launch {
            val result = movieUseCase.getMovies(sortMode)
            movies.value = result
        }
    }

    fun getFavoriteMovies(database: AppDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieList = movieUseCase.getFavorites(database)
            movies.postValue(movieList)
        }
    }
}
