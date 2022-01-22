package com.example.mymovies.utils;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory;

import com.example.mymovies.data.repository.MoviesLocalRepositoryImpl;
import com.example.mymovies.data.sources.local.mapper.MoviesLocalMapper;
import com.example.mymovies.model.businesslogic.AddFavoriteMovieUseCase;
import com.example.mymovies.model.businesslogic.DeleteFavoriteMovieUseCase;
import com.example.mymovies.model.businesslogic.GetAllFavoriteMoviesUseCase;
import com.example.mymovies.model.businesslogic.GetFavoriteMovieUseCase;
import com.example.mymovies.model.businesslogic.UpdateFavoriteMovieUseCase;
import com.example.mymovies.viewmodel.MoviesViewModel;

public class ViewModelFactory extends NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final Application application;
    private final AddFavoriteMovieUseCase addFavoriteMovieUseCase;
    private final DeleteFavoriteMovieUseCase deleteFavoriteMovieUseCase;
    private final UpdateFavoriteMovieUseCase updateFavoriteMovieUseCase;
    private final GetFavoriteMovieUseCase getFavoriteMovieUseCase;
    private final GetAllFavoriteMoviesUseCase getAllFavoriteMoviesUseCase;

    public ViewModelFactory(Application application, AddFavoriteMovieUseCase addFavoriteMovieUseCase,
                            DeleteFavoriteMovieUseCase deleteFavoriteMovieUseCase,
                            UpdateFavoriteMovieUseCase updateFavoriteMovieUseCase,
                            GetFavoriteMovieUseCase getFavoriteMovieUseCase,
                            GetAllFavoriteMoviesUseCase getAllFavoriteMoviesUseCase) {
        this.application = application;
        this.addFavoriteMovieUseCase = addFavoriteMovieUseCase;
        this.deleteFavoriteMovieUseCase = deleteFavoriteMovieUseCase;
        this.updateFavoriteMovieUseCase = updateFavoriteMovieUseCase;
        this.getFavoriteMovieUseCase = getFavoriteMovieUseCase;
        this.getAllFavoriteMoviesUseCase = getAllFavoriteMoviesUseCase;
    }

    public static ViewModelFactory getInstance(Application application){
        if (INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE == null){
                    Context context = application.getApplicationContext();
                    MoviesLocalMapper mapper = new MoviesLocalMapper();
                    MoviesLocalRepositoryImpl moviesLocalRepository = new MoviesLocalRepositoryImpl(context, mapper);
                    INSTANCE = new ViewModelFactory(application,
                            new AddFavoriteMovieUseCase(moviesLocalRepository),
                            new DeleteFavoriteMovieUseCase(moviesLocalRepository),
                            new UpdateFavoriteMovieUseCase(moviesLocalRepository),
                            new GetFavoriteMovieUseCase(moviesLocalRepository),
                            new GetAllFavoriteMoviesUseCase(moviesLocalRepository));
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(MoviesViewModel.class)){
            return (T) new MoviesViewModel(application, addFavoriteMovieUseCase, deleteFavoriteMovieUseCase,
                    updateFavoriteMovieUseCase, getFavoriteMovieUseCase, getAllFavoriteMoviesUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
