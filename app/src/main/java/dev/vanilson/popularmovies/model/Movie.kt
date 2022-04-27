package dev.vanilson.popularmovies.model

import com.google.gson.annotations.SerializedName

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
)