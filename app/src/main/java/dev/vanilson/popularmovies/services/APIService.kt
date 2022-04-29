package dev.vanilson.popularmovies.services

import androidx.lifecycle.MutableLiveData
import dev.vanilson.popularmovies.io.Endpoint
import dev.vanilson.popularmovies.io.Network
import dev.vanilson.popularmovies.model.*
import dev.vanilson.popularmovies.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIService {
    fun getMovies(sortMode: String?, liveData: MutableLiveData<List<Movie>>) {
        val retrofitClient = Network
            .getRetrofitInstance(Constants.MOVIES_URL)

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getMovies(sortMode ?: Constants.POPULAR_SORTING, Constants.API_KEY)

        callback.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                liveData.postValue(response.body()?.results)
            }
        })
    }

    fun getReviews(movieId: Int, liveData: MutableLiveData<List<Review>>) {

        val retrofitClient = Network
            .getRetrofitInstance(Constants.MOVIES_URL)

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getReviews(movieId, Constants.API_KEY)

        callback.enqueue(object : Callback<ReviewResponse> {
            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                liveData.postValue(response.body()?.results)
            }
        })
    }

    fun getTrailers(movieId: Int, liveData: MutableLiveData<List<Trailer>>) {

        val retrofitClient = Network
            .getRetrofitInstance(Constants.MOVIES_URL)

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getTrailers(movieId, Constants.API_KEY)

        callback.enqueue(object : Callback<TrailerResponse> {
            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                liveData.postValue(response.body()?.results)
            }
        })
    }
}