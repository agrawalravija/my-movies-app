package com.example.mymovies.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mymovies.R
import com.example.mymovies.model.Movie
import com.example.mymovies.viewmodel.MoviesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections.emptyList

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private val moviesViewModel: MoviesViewModel,
    private val isFavoriteFragment: Boolean,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int  = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(this.movies.size, movies.size-1)
    }

    fun clear() {
        this.movies = emptyList()
    }

    fun fillList(items: List<Movie>) {
        this.movies = items as MutableList<Movie>
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val favorite : FloatingActionButton = itemView.findViewById(R.id.favorite_fab)

        @SuppressLint("RestrictedApi")
        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            if(isFavoriteFragment)
                favorite.visibility = View.INVISIBLE

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
            favorite.setOnClickListener {
                moviesViewModel.toggleFavorite(movie)
                //TODO: Update favorite button image
                /*if(isFavorite)
                    favorite.setImageResource(R.drawable.ic_baseline_star_24)
                else
                    favorite.setImageResource(R.drawable.ic_star_border_black_24dp)*/
            }
            moviesViewModel.fetchFavoriteMovieState(movie)
        }
    }
}