package com.example.mymovies.model.businesslogic

import com.example.mymovies.data.repository.MoviesLocalRepositoryImpl
import com.example.mymovies.model.Movie
import io.reactivex.rxjava3.core.Observable

class GetAllFavoriteMoviesUseCase(private val moviesLocalRepository: MoviesLocalRepositoryImpl) {
    fun execute(): Observable<List<Movie>> {
        return moviesLocalRepository.getFavoriteMovies()
    }
}