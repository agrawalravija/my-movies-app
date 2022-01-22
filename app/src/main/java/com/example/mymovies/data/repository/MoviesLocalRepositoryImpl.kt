package com.example.mymovies.data.repository

import android.content.Context
import com.example.mymovies.data.sources.local.db.MoviesDatabase
import com.example.mymovies.data.sources.local.mapper.MoviesLocalMapper
import com.example.mymovies.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class MoviesLocalRepositoryImpl(private val context: Context, private val moviesLocalMapper: MoviesLocalMapper) {
    fun getFavoriteMovies(): Observable<List<Movie>> {
        return MoviesDatabase.invoke(context).movieDao().getFavoriteMovies().map {
            it.map { movie ->
                moviesLocalMapper.mapFromLocal(movie)
            }
        }
    }

    fun getFavoriteMovie(id: Long): Single<Movie> {
        return MoviesDatabase.invoke(context).movieDao().getFavoriteMovie(id).map {
            moviesLocalMapper.mapFromLocal(it)
        }
    }

    fun addFavoriteMovie(movie: Movie): Completable {
        return MoviesDatabase.invoke(context).movieDao().addFavoriteMovie(moviesLocalMapper.mapToLocal(movie))
    }

    fun deleteFavoriteMovie(movie: Movie): Completable {
        return MoviesDatabase.invoke(context).movieDao().deleteFavoriteMovie(moviesLocalMapper.mapToLocal(movie))
    }

    fun updateFavoriteMovie(movie: Movie): Completable {
        return MoviesDatabase.invoke(context).movieDao().updateFavoriteMovie(moviesLocalMapper.mapToLocal(movie))
    }
}