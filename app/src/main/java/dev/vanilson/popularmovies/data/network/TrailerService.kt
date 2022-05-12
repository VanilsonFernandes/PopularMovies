package dev.vanilson.popularmovies.data.network

import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.data.model.TrailerResponse
import dev.vanilson.popularmovies.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TrailerService {

    private val retrofit = NetworkService.getRetrofitInstance(Constants.MOVIES_URL)

    suspend fun getTrailers(movieId: Int): List<Trailer> {
        return withContext(Dispatchers.IO) {
            var response: Response<TrailerResponse>? = null

            try {
                response = retrofit.create(TrailerApiClient::class.java)
                    .getTrailers(movieId, Constants.API_KEY)
            } catch (e: Exception) {
                println("getTrailers.error: $e")
            }
            response?.body()?.results ?: emptyList()
        }
    }
}