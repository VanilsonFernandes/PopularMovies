package dev.vanilson.popularmovies.ui.view.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.databinding.TrailerListItemBinding
import dev.vanilson.popularmovies.utils.Constants.Companion.YOUTUBE_URL


class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.TrailersAdapterViewHolder?>() {
    var trailers: List<Trailer>? = null
    lateinit var mContext: Context
    lateinit var binding: TrailerListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersAdapterViewHolder {
        mContext = parent.context
        binding = TrailerListItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return TrailersAdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TrailersAdapterViewHolder, position: Int) {
        binding.tvTrailerName.text = trailers?.get(position)?.name;
    }

    override fun getItemCount(): Int {
        return trailers?.size ?: 0
    }

    fun updateTrailers(trailers: List<Trailer>?) {
        this.trailers = trailers
        notifyDataSetChanged()
    }

    inner class TrailersAdapterViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        override fun onClick(v: View) {
            try {
                val (key) = trailers!![adapterPosition]
                val youtubeUri: Uri = Uri.parse(YOUTUBE_URL + key)
                val intent = Intent(Intent.ACTION_VIEW, youtubeUri)
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