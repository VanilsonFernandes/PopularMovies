package dev.vanilson.popularmovies.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.popularmovies.data.model.Review
import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.domain.ReviewUseCase
import dev.vanilson.popularmovies.domain.TrailerUseCase
import kotlinx.coroutines.launch

class MovieDetailViewModel() : ViewModel() {

    var reviewUseCase = ReviewUseCase()
    var trailerUseCase = TrailerUseCase()

    var reviews: MutableLiveData<List<Review>> = MutableLiveData()
    var trailers: MutableLiveData<List<Trailer>> = MutableLiveData()


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
}
