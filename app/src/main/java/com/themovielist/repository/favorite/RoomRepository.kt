package com.themovielist.repository.favorite

import com.themovielist.model.entity.MovieFavoriteWatchedEntityModel
import javax.inject.Inject

class RoomRepository @Inject constructor(private val theMovieListDB: TheMovieListDB) {
    suspend fun getFavoriteWatchedMovieList(): List<MovieFavoriteWatchedEntityModel> = theMovieListDB.movieDAO().getFavoriteWatchedMovieList()
}