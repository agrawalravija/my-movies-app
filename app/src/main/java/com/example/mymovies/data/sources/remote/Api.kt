package com.example.mymovies.data.sources.remote

import com.example.mymovies.model.TopRatedMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "b65aaae0dff4e5490b2459e30888d957",
        @Query("page") page: Int
    ): Call<TopRatedMoviesResponse>

}