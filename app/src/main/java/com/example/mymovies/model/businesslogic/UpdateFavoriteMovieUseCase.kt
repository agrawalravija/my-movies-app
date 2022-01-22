package com.example.mymovies.model.businesslogic

import com.example.mymovies.data.repository.MoviesLocalRepositoryImpl
import com.example.mymovies.model.Movie
import io.reactivex.rxjava3.core.Completable

class UpdateFavoriteMovieUseCase(private val moviesLocalRepository: MoviesLocalRepositoryImpl) {
    fun execute(movie: Movie): Completable {
        return moviesLocalRepository.updateFavoriteMovie(movie)
    }
}