package com.example.mymovies.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager

import com.example.mymovies.R
import com.example.mymovies.data.Resource
import com.example.mymovies.model.Movie
import com.example.mymovies.utils.ViewModelUtils
import com.example.mymovies.viewmodel.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var favMoviesAdapter: MoviesAdapter
    private val noOfColumns = 3
    private lateinit var favMoviesLayoutMgr: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelUtils.obtainViewModel(activity, MoviesViewModel::class.java)

        favMoviesLayoutMgr = GridLayoutManager(
            context,
            noOfColumns
        )
        favorite_movies.layoutManager = favMoviesLayoutMgr

        favMoviesAdapter = MoviesAdapter(mutableListOf(), viewModel, true){ movie -> viewModel.showMovieDetails(movie) }
        favorite_movies.adapter = favMoviesAdapter

        viewModel.fetchFavoriteMovies()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteMoviesState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
    }

    private fun handleFavoriteMoviesDataState(state: Resource<List<Movie>>) {
        if (state.status == Resource.Status.SUCCESS) {
            loadMovies(state.data)
        }
        else if (state.status == Resource.Status.ERROR) {
            Toast.makeText(context,getString(R.string.error_fetch_movies),Toast.LENGTH_LONG).show()
        }
    }

    private fun loadMovies(movies: List<Movie>?) {
        movies?.let {
            favMoviesAdapter.clear()
            favMoviesAdapter.fillList(it)
        }
    }


}
