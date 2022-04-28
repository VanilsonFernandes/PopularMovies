package dev.vanilson.popularmovies.services

import androidx.lifecycle.MutableLiveData
import dev.vanilson.popularmovies.io.Endpoint
import dev.vanilson.popularmovies.io.Network
import dev.vanilson.popularmovies.model.Movie
import dev.vanilson.popularmovies.model.MovieResponse
import dev.vanilson.popularmovies.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieService {
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
}