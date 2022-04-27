package dev.vanilson.popularmovies.services

import android.content.Context
import android.widget.Toast
import dev.vanilson.popularmovies.io.Endpoint
import dev.vanilson.popularmovies.io.Network
import dev.vanilson.popularmovies.model.MovieResponse
import dev.vanilson.popularmovies.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieService {
    public fun getMovies(context: Context) {
        val retrofitClient = Network
            .getRetrofitInstance(Constants.MOVIES_URL)

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getMovies(Constants.POPULAR_SORTING, Constants.API_KEY)

        callback.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                println("error = ${t.message}")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.results?.forEach { movie ->
                    println("movie = ${movie.title}")
                }
            }
        })
    }
}