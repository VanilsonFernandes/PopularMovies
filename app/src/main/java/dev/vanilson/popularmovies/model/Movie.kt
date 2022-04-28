package dev.vanilson.popularmovies.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    var id: Int,
    @SerializedName("vote_average")
    var voteAverage: Double,
    var title: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    var overview: String,
) : Parcelable