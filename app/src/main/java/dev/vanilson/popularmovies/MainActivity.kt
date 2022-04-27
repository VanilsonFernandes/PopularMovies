package dev.vanilson.popularmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.vanilson.popularmovies.services.MovieService

class MainActivity : AppCompatActivity() {
    private val movieService = MovieService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieService.getMovies(baseContext);
    }

}