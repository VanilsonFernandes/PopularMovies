package dev.vanilson.popularmovies.data.model

data class MovieResponse(
    var page: Int,
    var results: List<Movie>
)

data class ReviewResponse(
    var page: Int,
    var results: List<Review>
)

data class TrailerResponse(
    var id: Int,
    var results: List<Trailer>
)