package dev.vanilson.popularmovies.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.data.model.Review
import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.domain.MovieUseCase
import dev.vanilson.popularmovies.domain.ReviewUseCase
import dev.vanilson.popularmovies.domain.TrailerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel() : ViewModel() {

    var reviewUseCase = ReviewUseCase()
    var trailerUseCase = TrailerUseCase()
    private var movieUseCase = MovieUseCase()

    var reviews: MutableLiveData<List<Review>> = MutableLiveData()
    var trailers: MutableLiveData<List<Trailer>> = MutableLiveData()
    var isFavorite: MutableLiveData<Boolean> = MutableLiveData()


    fun getReviews(movieId: Int) {
        viewModelScope.launch {
            val result = reviewUseCase(movieId)
            reviews.postValue(result)
        }
    }

    fun getTrailers(movieId: Int) {
        viewModelScope.launch {
            val result = trailerUseCase(movieId)
            trailers.postValue(result)
        }

    }

    fun isFavorite(database: AppDatabase, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieUseCase.isFavorite(database, movieId)
            isFavorite.postValue(result)
        }
    }

    fun addToFavorites(database: AppDatabase, movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.addToFavorite(database, movie)
        }
    }

    fun removeFromFavorites(database: AppDatabase, movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            database.movieDao().delete(movie)
        }
    }


}
