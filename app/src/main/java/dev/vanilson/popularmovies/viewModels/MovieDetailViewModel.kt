package dev.vanilson.popularmovies.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.vanilson.popularmovies.model.Review
import dev.vanilson.popularmovies.model.Trailer
import dev.vanilson.popularmovies.services.APIService

class MovieDetailViewModel() : ViewModel() {

    private val movieService = APIService()
    var reviews: MutableLiveData<List<Review>> = MutableLiveData()
    var trailers: MutableLiveData<List<Trailer>> = MutableLiveData()

    fun getReviews(movieId: Int): MutableLiveData<List<Review>> {
        movieService.getReviews(movieId, reviews)
        return reviews
    }

    fun getTrailers(movieId: Int): MutableLiveData<List<Trailer>> {
        movieService.getTrailers(movieId, trailers)
        return trailers
    }
}
