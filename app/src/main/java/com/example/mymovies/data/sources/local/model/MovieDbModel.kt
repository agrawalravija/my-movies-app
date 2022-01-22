package com.example.mymovies.data.sources.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class MovieDbModel(
    @PrimaryKey val id : Long,
    @ColumnInfo(name = "poster_path") val poster_path : String?,
    @ColumnInfo(name = "backdrop_path") val backdrop_path : String?,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "vote_average") val vote_average : Float?,
    @ColumnInfo(name = "overview") val overview : String?,
    @ColumnInfo(name = "release_date") val release_date : String?
) {
}