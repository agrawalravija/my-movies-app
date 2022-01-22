package com.example.mymovies.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
internal class TabAdapter (
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TopRatedMoviesFragment()
            }
            1 -> {
                FavoriteFragment()
            }
            else -> TopRatedMoviesFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}