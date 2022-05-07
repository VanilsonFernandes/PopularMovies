package dev.vanilson.popularmovies.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.vanilson.popularmovies.R
import dev.vanilson.popularmovies.R.id
import dev.vanilson.popularmovies.R.layout
import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.ui.view.adapters.MoviesAdapter
import dev.vanilson.popularmovies.ui.viewModels.MoviesViewModel
import dev.vanilson.popularmovies.utils.Constants.Companion.FAVORITE_SORTING
import dev.vanilson.popularmovies.utils.Constants.Companion.NUMBER_OF_COLUMNS
import dev.vanilson.popularmovies.utils.Constants.Companion.POPULAR_SORTING
import dev.vanilson.popularmovies.utils.Constants.Companion.TOP_RATED_SORTING


class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mErrorMessageDisplay: TextView
    private lateinit var mLoadingIndicator: ProgressBar
    private lateinit var mMoviesAdapter: MoviesAdapter
    private val mMoviesViewModel: MoviesViewModel by viewModels()
    private var showingFavorites = false

    private val SHOWING_FAVORITES_KEY = "showingFavoritesKey"

    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        mRecyclerView = findViewById(id.movies_recyclerview)
        mErrorMessageDisplay = findViewById(id.tv_error_message_display)
        mLoadingIndicator = findViewById(id.pb_loading_indicator)
        mMoviesAdapter = MoviesAdapter()

        val layoutManager = GridLayoutManager(this, NUMBER_OF_COLUMNS)

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mMoviesAdapter

        mLoadingIndicator.visibility = View.VISIBLE;

        mMoviesViewModel.movies.observe(this) { movies ->
            mMoviesAdapter.updateMovies(movies)
            if (movies != null && movies.isNotEmpty()) {
                showDataView()
            } else {
                showErrorMessage()
            }
            mLoadingIndicator.visibility = View.INVISIBLE;
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
        outState.putBoolean(SHOWING_FAVORITES_KEY, showingFavorites);
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        showingFavorites = savedInstanceState.getBoolean(SHOWING_FAVORITES_KEY, false)
    }

    private fun loadData(sortMode: String) {
        mLoadingIndicator.visibility = View.VISIBLE;
        if (sortMode == FAVORITE_SORTING) {
            mMoviesViewModel.getFavoriteMovies(database)
            return
        }
        mMoviesViewModel.getMovies(sortMode);
    }

    private fun showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE)
        mErrorMessageDisplay.setVisibility(View.VISIBLE)
    }

    private fun showDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE)
        mRecyclerView.setVisibility(View.VISIBLE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
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