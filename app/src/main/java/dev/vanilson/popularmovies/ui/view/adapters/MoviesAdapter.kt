package dev.vanilson.popularmovies.ui.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.vanilson.popularmovies.data.model.Movie
import dev.vanilson.popularmovies.databinding.MovieListItemBinding
import dev.vanilson.popularmovies.ui.view.MovieDetailActivity
import dev.vanilson.popularmovies.utils.Constants.Companion.IMG_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder?>() {
    var movies: List<Movie>? = null
    lateinit var mContext: Context
    lateinit var binding: MovieListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapterViewHolder {
        mContext = parent.context

        binding = MovieListItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MoviesAdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesAdapterViewHolder, position: Int) {
        Picasso.get().load(IMG_URL + movies!![position].posterPath)
            .into(binding.ivMovieItem)
    }

    override fun getItemCount(): Int {
        return if (null == movies) 0 else movies!!.size
    }

    fun updateMovies(movies: List<Movie>?) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MoviesAdapterViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        override fun onClick(v: View) {
            try {
                val movie: Movie = movies!![adapterPosition]
                val intent = Intent(mContext, MovieDetailActivity::class.java)
                intent.putExtra("movie", movie)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        init {
            binding.root.setOnClickListener(this)
        }
    }
}