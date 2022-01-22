package com.example.mymovies.model.businesslogic

import com.example.mymovies.data.repository.MoviesLocalRepositoryImpl
import com.example.mymovies.model.Movie
import io.reactivex.rxjava3.core.Single

class GetFavoriteMovieUseCase(private val moviesLocalRepository: MoviesLocalRepositoryImpl) {
    fun execute(id: Long): Single<Movie> {
        return moviesLocalRepository.getFavoriteMovie(id)
    }
}