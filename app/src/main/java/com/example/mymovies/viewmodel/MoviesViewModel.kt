package com.example.mymovies.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovies.data.Resource
import com.example.mymovies.model.Movie
import com.example.mymovies.model.businesslogic.*
import com.example.mymovies.view.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoviesViewModel(
    private val application : Application,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase
) : ViewModel() {

    var disposable: Disposable? = null
    private val favoritesStateFlow = MutableStateFlow<Resource<Boolean>>(Resource.empty())
    private val stateFlow = MutableStateFlow<Resource<List<Movie>>>(Resource.empty())

    val favoritesState: StateFlow<Resource<Boolean>>
        get() = favoritesStateFlow

    val favoriteMoviesState: StateFlow<Resource<List<Movie>>>
        get() = stateFlow

    fun toggleFavorite(movie: Movie){
        favoritesStateFlow.value = Resource.loading()

        disposable = getFavoriteMovieUseCase.execute(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // favorite movie exists, remove from favorites
                deleteFavoriteMovie(movie)
            }, {
                // favorite movie does not exist, add to favorites
                addFavoriteMovie(movie)
            })
    }

    fun deleteFavoriteMovie(movie: Movie) {
        favoritesStateFlow.value = Resource.loading()

        disposable = deleteFavoriteMovieUseCase.execute(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favoritesStateFlow.value = Resource.success(false)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    favoritesStateFlow.value = Resource.error(it)
                }
            })
    }

    fun updateFavoriteMovie(movie: Movie) {
        favoritesStateFlow.value = Resource.loading()

        disposable = updateFavoriteMovieUseCase.execute(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favoritesStateFlow.value = Resource.success(true)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    favoritesStateFlow.value = Resource.error(it)
                }
            })
    }

    fun addFavoriteMovie(movie: Movie) {
        favoritesStateFlow.value = Resource.loading()

        disposable = addFavoriteMovieUseCase.execute(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favoritesStateFlow.value = Resource.success(true)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    favoritesStateFlow.value = Resource.error(it)
                }
            })
    }

    fun fetchFavoriteMovies() {
        stateFlow.value = Resource.loading()

        disposable = getAllFavoriteMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlow.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    stateFlow.value = Resource.error(it)
                }
            })
    }

    fun fetchFavoriteMovieState(movie: Movie) {
        favoritesStateFlow.value = Resource.loading()

        disposable = getFavoriteMovieUseCase.execute(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // favorite movie exists, update data first
                updateFavoriteMovie(movie)
                favoritesStateFlow.value = Resource.success(true)
            }, {
                // favorite movie does not exist
                favoritesStateFlow.value = Resource.success(false)
            })
    }

    //TODO: Remove intent and add navigation
    //TODO: Fetch movie details from movie db
    fun showMovieDetails(movie: Movie) {
        val intent = Intent(application, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}