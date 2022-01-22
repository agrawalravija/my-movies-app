package com.example.mymovies.view

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mymovies.R
import com.example.mymovies.data.Resource
import com.example.mymovies.data.repository.MoviesRemoteRepository
import com.example.mymovies.model.Movie
import com.example.mymovies.utils.ViewModelUtils
import com.example.mymovies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.top_rated_movies_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TopRatedMoviesFragment : Fragment() {

    //private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private val noOfColumns = 3
    private lateinit var topRatedMoviesLayoutMgr: GridLayoutManager
    private lateinit var viewModel: MoviesViewModel


    private var topRatedMoviesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_rated_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelUtils.obtainViewModel(activity,MoviesViewModel::class.java)
        //topRatedMovies = findViewById(R.id.top_rated_movies) as RecyclerView
        topRatedMoviesLayoutMgr = GridLayoutManager(
            context,
            noOfColumns
        )
        top_rated_movies.layoutManager = topRatedMoviesLayoutMgr

        topRatedMoviesAdapter = MoviesAdapter(mutableListOf(), viewModel, false){ movie -> viewModel.showMovieDetails(movie) }
        top_rated_movies.adapter = topRatedMoviesAdapter

        viewModel.fetchFavoriteMovies()
        getTopRatedMovies()
    }

    //TODO: Implement the functionality using coroutines
    private fun getTopRatedMovies(){
        MoviesRemoteRepository.getTopRatedMovies(
            topRatedMoviesPage,
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        //Log.d("MainActivity", "Movies: $movies")
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()  //reattach the OnScrollListener
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        top_rated_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //the total number of movies inside our topRatedMoviesAdapter.
                // This will keep increasing the more we call topRatedMoviesAdapter.appendMovies()
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount

                //the current number of child views attached to the RecyclerView
                // that are currently being recycled over and over again.
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                //The condition will be true if the user has scrolled past halfway plus a buffered value of visibleItemCount.
                //After condition is met, disable the scroll listener since we only want this code to run once.
                // Next, increment topRatedMoviesPage and then call getTopRatedMovies().
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    top_rated_movies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(context, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}
