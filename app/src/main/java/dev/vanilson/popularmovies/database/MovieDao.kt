package dev.vanilson.popularmovies.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.vanilson.popularmovies.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = (:movieId)")
    fun findById(movieId: Int): Movie


    @Insert
    fun insertAll(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)

}