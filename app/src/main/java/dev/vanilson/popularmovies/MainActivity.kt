package dev.vanilson.popularmovies

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
import dev.vanilson.popularmovies.adapters.MoviesAdapter
import dev.vanilson.popularmovies.database.AppDatabase
import dev.vanilson.popularmovies.utils.Constants.Companion.NUMBER_OF_COLUMNS
import dev.vanilson.popularmovies.utils.Constants.Companion.POPULAR_SORTING
import dev.vanilson.popularmovies.utils.Constants.Companion.TOP_RATED_SORTING
import dev.vanilson.popularmovies.viewModels.MoviesViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mErrorMessageDisplay: TextView
    private lateinit var mLoadingIndicator: ProgressBar
    private lateinit var mMoviesAdapter: MoviesAdapter
    private val mMoviesViewModel: MoviesViewModel by viewModels()
    private var showingFavorites = false

    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.movies_recyclerview)
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display)
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator)
        mMoviesAdapter = MoviesAdapter()

        val layoutManager = GridLayoutManager(this, NUMBER_OF_COLUMNS)

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mMoviesAdapter

        mLoadingIndicator.visibility = View.VISIBLE;
        mMoviesViewModel.movies.observe(this) { movies ->
            mMoviesAdapter.updateMovies(movies)
            if (movies != null) {
                showDataView()
            } else {
                showErrorMessage()
            }
            mLoadingIndicator.visibility = View.INVISIBLE;
        }
        if (mMoviesViewModel.movies.value == null) {
            mMoviesViewModel.getMovies(null)
        }

    }

    private fun loadData(sortMode: String?) {
        mLoadingIndicator.visibility = View.VISIBLE;
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
            val movies = this.database.movieDao().getAll()
            mMoviesViewModel.movies.postValue(movies)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}