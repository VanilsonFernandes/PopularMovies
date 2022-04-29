package dev.vanilson.popularmovies

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
import dev.vanilson.popularmovies.adapters.ReviewsAdapter
import dev.vanilson.popularmovies.adapters.TrailersAdapter
import dev.vanilson.popularmovies.model.Movie
import dev.vanilson.popularmovies.utils.Constants.Companion.IMG_POSTER_URL
import dev.vanilson.popularmovies.viewModels.MovieDetailViewModel


class MovieDetail : AppCompatActivity() {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        tvTitle = findViewById(R.id.tv_movie_title)
        tvRating = findViewById(R.id.tv_movie_rating)
        tvSynopsis = findViewById(R.id.tv_movie_synopsis)
        tvDate = findViewById(R.id.tv_movie_date)
        ivPoster = findViewById(R.id.iv_movie_poster)
        cbFavorite = findViewById(R.id.cb_favorite)
        mLoadingIndicator = findViewById(R.id.pb_detail_loading_indicator)
        vContainer = findViewById(R.id.detail_container)
        rvTrailers = findViewById(R.id.rvTrailers)
        rvReviews = findViewById(R.id.rvReviews)
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
        cbFavorite.isChecked = false //isFavorite()


        val reviews = mMovieDetailViewModel.reviews.value
        val trailers = mMovieDetailViewModel.trailers.value

        if (reviews == null && trailers == null) {
            loadData(movie?.id)
        } else {
            mReviewsAdapter.setReviews(reviews)
            mTrailersAdapter.updateTrailers(trailers)
            showData()
        }

    }

    private fun loadData(movieId: Int?) {
        if (movieId != null) {
            mMovieDetailViewModel.getReviews(movieId).observe(this) { reviews ->
                mReviewsAdapter.setReviews(reviews)
            }
            mMovieDetailViewModel.getTrailers(movieId).observe(this) { trailers ->
                mTrailersAdapter.updateTrailers(trailers)
                showData()
            }
        }
    }

    private fun showData() {
        vContainer.visibility = View.VISIBLE
        mLoadingIndicator.visibility = View.GONE
    }
}