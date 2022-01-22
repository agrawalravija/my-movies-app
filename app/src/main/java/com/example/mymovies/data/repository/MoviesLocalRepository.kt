package com.example.mymovies.data.repository

import com.example.mymovies.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MoviesLocalRepository {

    fun getFavoriteMovies(): Observable<List<Movie>>

    fun getFavoriteMovie(id: Long): Single<Movie>

    fun addFavoriteMovie(movie: Movie): Completable

    fun deleteFavoriteMovie(movie: Movie): Completable

    fun updateFavoriteMovie(movie: Movie): Completable

}