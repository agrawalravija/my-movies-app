package com.example.mymovies.data.repository

import com.example.mymovies.model.Movie
import com.example.mymovies.model.TopRatedMoviesResponse
import com.example.mymovies.data.sources.remote.Api
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRemoteRepository {

    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedMovies(page = page)
            .enqueue(object : Callback<TopRatedMoviesResponse> {
                override fun onResponse(
                    call: Call<TopRatedMoviesResponse>,
                    response: Response<TopRatedMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            //Log.d("Repository", "Movies: ${responseBody.movies}")
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            //Log.d("Repository", "Failed to get response")
                            onError.invoke()
                        }
                    }
                    else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<TopRatedMoviesResponse>, t: Throwable) {
                    //Log.e("Repository", "onFailure", t)
                    onError.invoke()
                }
            })
    }
}