package dev.vanilson.popularmovies.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import dev.vanilson.popularmovies.R
import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.databinding.ActivityMainBinding
import dev.vanilson.popularmovies.ui.view.adapters.MoviesAdapter
import dev.vanilson.popularmovies.ui.viewModels.MoviesViewModel
import dev.vanilson.popularmovies.utils.Constants.Companion.FAVORITE_SORTING
import dev.vanilson.popularmovies.utils.Constants.Companion.NUMBER_OF_COLUMNS
import dev.vanilson.popularmovies.utils.Constants.Companion.POPULAR_SORTING
import dev.vanilson.popularmovies.utils.Constants.Companion.TOP_RATED_SORTING


class MainActivity : AppCompatActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private val mMoviesViewModel: MoviesViewModel by viewModels()
    private var showingFavorites = false

    private val SHOWING_FAVORITES_KEY = "showingFavoritesKey"

    private val database by lazy { AppDatabase.getDatabase(this) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mMoviesAdapter = MoviesAdapter()

        val layoutManager = GridLayoutManager(this, NUMBER_OF_COLUMNS)

        binding.moviesRecyclerview.layoutManager = layoutManager
        binding.moviesRecyclerview.setHasFixedSize(true)
        binding.moviesRecyclerview.adapter = mMoviesAdapter

        binding.pbLoadingIndicator.visibility = View.VISIBLE

        mMoviesViewModel.movies.observe(this) { movies ->
            mMoviesAdapter.updateMovies(movies)
            if (movies != null && movies.isNotEmpty()) {
                showDataView()
            } else {
                showErrorMessage()
            }
            binding.pbLoadingIndicator.visibility = View.INVISIBLE
        }

    }

    override fun onResume() {
        super.onResume()
        if (mMoviesViewModel.movies.value == null) {
            loadData(POPULAR_SORTING)
        } else if (showingFavorites) {
            loadData(FAVORITE_SORTING)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(SHOWING_FAVORITES_KEY, showingFavorites)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        showingFavorites = savedInstanceState.getBoolean(SHOWING_FAVORITES_KEY, false)
    }

    private fun loadData(sortMode: String) {
        binding.pbLoadingIndicator.visibility = View.VISIBLE
        if (sortMode == FAVORITE_SORTING) {
            mMoviesViewModel.getFavoriteMovies(database)
            return
        }
        mMoviesViewModel.getMovies(sortMode)
    }

    private fun showErrorMessage() {
        binding.moviesRecyclerview.visibility = View.INVISIBLE
        binding.tvErrorMessageDisplay.visibility = View.VISIBLE
    }

    private fun showDataView() {
        binding.tvErrorMessageDisplay.visibility = View.INVISIBLE
        binding.moviesRecyclerview.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.miTopRated) {
            showingFavorites = false
            mMoviesAdapter.movies = null
            loadData(TOP_RATED_SORTING)
            return true
        }
        if (id == R.id.miPopular) {
            showingFavorites = false
            mMoviesAdapter.movies = null
            loadData(POPULAR_SORTING)
            return true
        }
        if (id == R.id.miFavorites) {
            showingFavorites = true
            mMoviesAdapter.movies = null
            loadData(FAVORITE_SORTING)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}