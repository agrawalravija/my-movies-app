package com.example.mymovies.model.businesslogic

import com.example.mymovies.data.repository.MoviesRemoteRepository
import com.example.mymovies.model.Movie
import io.reactivex.rxjava3.core.Single

class GetSingleMovieUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {
}