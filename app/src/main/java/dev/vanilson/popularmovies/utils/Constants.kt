package dev.vanilson.popularmovies.utils

class Constants {

    companion object {
        //Aqui vai a sua chave do IMDB
        const val API_KEY = "";

        const val MOVIES_URL = "http://api.themoviedb.org/3/movie/";
        const val IMG_URL = "http://image.tmdb.org/t/p/w500/";
        const val IMG_POSTER_URL = "http://image.tmdb.org/t/p/w1280/";
        const val YOUTUBE_URL = "https://www.youtube.com/watch?v=";

        const val NUMBER_OF_COLUMNS = 2;
        const val POPULAR_SORTING = "popular";
        const val TOP_RATED_SORTING = "top_rated";
        const val FAVORITE_SORTING = "favorites";
    }
}