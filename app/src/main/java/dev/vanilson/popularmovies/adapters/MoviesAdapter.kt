package dev.vanilson.popularmovies.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.vanilson.popularmovies.MovieDetail
import dev.vanilson.popularmovies.R
import dev.vanilson.popularmovies.model.Movie
import dev.vanilson.popularmovies.utils.Constants.Companion.IMG_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder?>() {
    var movies: List<Movie>? = null
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapterViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(R.layout.movie_list_item, parent, false)
        return MoviesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesAdapterViewHolder, position: Int) {
        Picasso.get().load(IMG_URL + movies!![position].posterPath)
            .into(holder.mMovieImageView)
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
        val mMovieImageView: ImageView = view.findViewById(R.id.iv_movie_item)
        override fun onClick(v: View) {
            try {
                val movie: Movie = movies!![adapterPosition]
                val intent = Intent(mContext, MovieDetail::class.java)
                intent.putExtra("movie", movie)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        init {
            view.setOnClickListener(this)
        }
    }
}