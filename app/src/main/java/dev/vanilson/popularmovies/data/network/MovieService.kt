package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.data.model.MovieResponse
import dev.vanilson.popularmovies.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieService {

    private val retrofit = NetworkService.getRetrofitInstance(Constants.MOVIES_URL)

    suspend fun getMovies(sortMode: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            var response: Response<MovieResponse>? = null

            try {
                response = retrofit.create(MovieApiClient::class.java)
                    .getMovies(sortMode, Constants.API_KEY)
            } catch (e: Exception) {
                println("getMovies.error: $e")
            }
            response?.body()?.results ?: emptyList()
        }
    }
}