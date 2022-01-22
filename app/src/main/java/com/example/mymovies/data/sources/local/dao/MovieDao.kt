package com.example.mymovies.data.sources.local.dao

import androidx.room.*
import com.example.mymovies.data.sources.local.model.MovieDbModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Observable<List<MovieDbModel>>

    @Query("SELECT * FROM favorite_movies WHERE id == :id")
    fun getFavoriteMovie(id: Long): Single<MovieDbModel>

    @Insert
    fun addFavoriteMovie(movie: MovieDbModel): Completable

    @Delete
    fun deleteFavoriteMovie(movie: MovieDbModel): Completable

    @Update
    fun updateFavoriteMovie(movie: MovieDbModel): Completable
}