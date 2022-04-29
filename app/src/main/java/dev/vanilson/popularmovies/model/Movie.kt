package dev.vanilson.popularmovies.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Movie(

    @PrimaryKey var id: Int,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average") var voteAverage: Double,

    @ColumnInfo(name = "title") var title: String,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path") var posterPath: String,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path") var backdropPath: String,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date") var releaseDate: String,

    @ColumnInfo(name = "overview") var overview: String,

    ) : Parcelable