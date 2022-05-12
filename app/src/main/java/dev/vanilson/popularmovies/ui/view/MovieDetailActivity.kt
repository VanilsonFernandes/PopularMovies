package dev.vanilson.popularmovies.ui.view

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.vanilson.popularmovies.R.id
import dev.vanilson.popularmovies.R.layout
import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.ui.view.adapters.ReviewsAdapter
import dev.vanilson.popularmovies.ui.view.adapters.TrailersAdapter
import dev.vanilson.popularmovies.ui.viewModels.MovieDetailViewModel
import dev.vanilson.popularmovies.utils.Constants.Companion.IMG_POSTER_URL


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var tvDate: TextView
    private lateinit var ivPoster: ImageView
    private lateinit var cbFavorite: CheckBox
    private var movie: Movie? = null
    private lateinit var mLoadingIndicator: ProgressBar
    private lateinit var vContainer: View
    private lateinit var rvTrailers: RecyclerView
    private lateinit var rvReviews: RecyclerView
    private lateinit var mTrailersAdapter: TrailersAdapter
    private lateinit var mReviewsAdapter: ReviewsAdapter

    private val mMovieDetailViewModel: MovieDetailViewModel by viewModels()
    private val database by lazy { AppDatabase.getDatabase(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_movie_detail)

        tvTitle = findViewById(id.tv_movie_title)
        tvRating = findViewById(id.tv_movie_rating)
        tvSynopsis = findViewById(id.tv_movie_synopsis)
        tvDate = findViewById(id.tv_movie_date)
        ivPoster = findViewById(id.iv_movie_poster)
        cbFavorite = findViewById(id.cb_favorite)
        mLoadingIndicator = findViewById(id.pb_detail_loading_indicator)
        vContainer = findViewById(id.detail_container)
        rvTrailers = findViewById(id.rvTrailers)
        rvReviews = findViewById(id.rvReviews)
        mTrailersAdapter = TrailersAdapter()
        mReviewsAdapter = ReviewsAdapter()
        val trailersLayoutManager = LinearLayoutManager(this)
        val reviewsLayoutManager = LinearLayoutManager(this)

        trailersLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvTrailers.setHasFixedSize(true)
        rvTrailers.layoutManager = trailersLayoutManager
        rvTrailers.adapter = mTrailersAdapter
        rvTrailers.isNestedScrollingEnabled = false
        rvTrailers.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        reviewsLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvReviews.setHasFixedSize(true)
        rvReviews.layoutManager = reviewsLayoutManager
        rvReviews.isNestedScrollingEnabled = false
        rvReviews.adapter = mReviewsAdapter

        val selfIntent = intent

        if (!selfIntent.hasExtra("movie")) {
            onBackPressed()
        }

        movie = selfIntent.getParcelableExtra("movie")

        tvTitle.text = movie?.title
        tvRating.text = movie?.voteAverage.toString()
        tvSynopsis.text = movie?.overview
        tvDate.text = movie?.releaseDate
        Picasso.get().load(IMG_POSTER_URL + movie?.backdropPath).into(ivPoster)
        cbFavorite.isChecked = false


        val reviewsVal = mMovieDetailViewModel.reviews.value
        val trailersVal = mMovieDetailViewModel.trailers.value

        mMovieDetailViewModel.reviews.observe(this) {
            mReviewsAdapter.setReviews(it)
        }

        mMovieDetailViewModel.trailers.observe(this) {
            mTrailersAdapter.updateTrailers(it)
            showData()
        }

        mMovieDetailViewModel.isFavorite.observe(this) {
            cbFavorite.isChecked = it
        }

        if (reviewsVal == null && trailersVal == null) {
            loadData(movie?.id)
        } else {
            mReviewsAdapter.setReviews(reviewsVal)
            mTrailersAdapter.updateTrailers(trailersVal)
            showData()
        }

    }

    private fun loadData(movieId: Int?) {
        if (movieId != null) {
            mMovieDetailViewModel.getReviews(movieId)
            mMovieDetailViewModel.getTrailers(movieId)
            mMovieDetailViewModel.isFavorite(this.database, movieId)
        }
    }

    private fun showData() {
        vContainer.visibility = View.VISIBLE
        mLoadingIndicator.visibility = View.GONE
    }

    fun toggleFavorite(view: View) {
        if (movie != null) {
            if (isFavorite()) {
                mMovieDetailViewModel.removeFromFavorites(database, movie!!)
                cbFavorite.isChecked = false
            } else {
                mMovieDetailViewModel.addToFavorites(database, movie!!)
                cbFavorite.isChecked = true
            }
        }
    }

    private fun isFavorite(): Boolean {
        return mMovieDetailViewModel.isFavorite.value == true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }
}