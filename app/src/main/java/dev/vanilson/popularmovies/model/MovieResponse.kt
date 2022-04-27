package dev.vanilson.popularmovies.model

data class MovieResponse(
    var page: Int,
    var results: List<Movie>
)
