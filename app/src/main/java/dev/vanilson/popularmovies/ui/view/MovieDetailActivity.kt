package dev.vanilson.popularmovies.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dev.vanilson.popularmovies.data.database.AppDatabase
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.databinding.ActivityMovieDetailBinding
import dev.vanilson.popularmovies.ui.view.adapters.ReviewsAdapter
import dev.vanilson.popularmovies.ui.view.adapters.TrailersAdapter
import dev.vanilson.popularmovies.ui.viewModels.MovieDetailViewModel
import dev.vanilson.popularmovies.utils.Constants.Companion.IMG_POSTER_URL


class MovieDetailActivity : AppCompatActivity() {

    private var movie: Movie? = null
    private lateinit var mTrailersAdapter: TrailersAdapter
    private lateinit var mReviewsAdapter: ReviewsAdapter

    private val mMovieDetailViewModel: MovieDetailViewModel by viewModels()
    private val database by lazy { AppDatabase.getDatabase(this) }

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mTrailersAdapter = TrailersAdapter()
        mReviewsAdapter = ReviewsAdapter()
        val trailersLayoutManager = LinearLayoutManager(this)
        val reviewsLayoutManager = LinearLayoutManager(this)

        trailersLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvTrailers.setHasFixedSize(true)
        binding.rvTrailers.layoutManager = trailersLayoutManager
        binding.rvTrailers.adapter = mTrailersAdapter
        binding.rvTrailers.isNestedScrollingEnabled = false
        binding.rvTrailers.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        reviewsLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvReviews.setHasFixedSize(true)
        binding.rvReviews.layoutManager = reviewsLayoutManager
        binding.rvReviews.isNestedScrollingEnabled = false
        binding.rvReviews.adapter = mReviewsAdapter

        val selfIntent = intent

        if (!selfIntent.hasExtra("movie")) {
            onBackPressed()
        }

        movie = selfIntent.getParcelableExtra("movie")

        binding.tvMovieTitle.text = movie?.title
        binding.tvMovieRating.text = movie?.voteAverage.toString()
        binding.tvMovieSynopsis.text = movie?.overview
        binding.tvMovieDate.text = movie?.releaseDate
        binding.cbFavorite.isChecked = false
        println(">>>> poster: " + IMG_POSTER_URL + movie?.backdropPath);
        Picasso
            .get()
            .load(IMG_POSTER_URL + movie?.backdropPath)
            .into(binding.ivMoviePoster)

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
            binding.cbFavorite.isChecked = it
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
        binding.detailContainer.visibility = View.VISIBLE
        binding.pbDetailLoadingIndicator.visibility = View.GONE
    }

    fun toggleFavorite(view: View) {
        if (movie != null) {
            if (isFavorite()) {
                mMovieDetailViewModel.removeFromFavorites(database, movie!!)
                binding.cbFavorite.isChecked = false
            } else {
                mMovieDetailViewModel.addToFavorites(database, movie!!)
                binding.cbFavorite.isChecked = true
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