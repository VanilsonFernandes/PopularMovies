package dev.vanilson.popularmovies.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.vanilson.popularmovies.R
import dev.vanilson.popularmovies.data.model.Trailer
import dev.vanilson.popularmovies.utils.Constants.Companion.YOUTUBE_URL


class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.TrailersAdapterViewHolder?>() {
    var trailers: List<Trailer>? = null
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersAdapterViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(R.layout.trailer_list_item, parent, false)
        return TrailersAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailersAdapterViewHolder, position: Int) {
        holder.mTrailerName.text = trailers?.get(position)?.name;
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

        val mTrailerName: TextView = view.findViewById(R.id.tv_trailer_name)

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