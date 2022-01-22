package com.example.mymovies.utils;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

//Class that will provide static ViewModel objects
public class ViewModelUtils {

    public static <T extends ViewModel> T obtainViewModel(FragmentActivity activity, Class<T> modelClass){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        T viewModel = ViewModelProviders.of(activity, factory).get(modelClass);

        return viewModel;
    }
}
