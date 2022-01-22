package com.example.mymovies.data.sources.local.mapper

import com.example.mymovies.data.sources.local.model.MovieDbModel
import com.example.mymovies.model.Movie

class MoviesLocalMapper {

    fun mapFromLocal(movieDbModel: MovieDbModel): Movie {
        return Movie(movieDbModel.id, movieDbModel.title, movieDbModel.overview.orEmpty(),
            movieDbModel.poster_path.orEmpty(), movieDbModel.backdrop_path.orEmpty(),
            movieDbModel.vote_average!!, movieDbModel.release_date.orEmpty())
    }

    fun mapToLocal(movie: Movie): MovieDbModel {
        return MovieDbModel(movie.id, movie.posterPath, movie.backdropPath, movie.title, movie.rating,
            movie.overview, movie.releaseDate)
    }
}