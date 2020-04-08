package com.themovielist.repository.favorite

import com.themovielist.model.entity.MovieFavoriteWatchedEntityModel
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieStore
import javax.inject.Inject

class RoomRepository
@Inject
constructor(
    private val theMovieListDB: TheMovieListDB,
    private val movieStore: MovieStore
) {
    suspend fun getFavoriteWatchedMovieList(): List<MovieFavoriteWatchedEntityModel> = theMovieListDB.movieDAO().getFavoriteWatchedMovieList()

    suspend fun updateMovie(movieModel: MovieModel) {
        val movieEntityModel = movieModel.toEntity()
        if (!movieModel.isFavorite && !movieModel.isWatched) {
            theMovieListDB.movieDAO().delete(movieEntityModel)
        } else {
            theMovieListDB.movieDAO().insert(movieEntityModel)
        }

        movieStore.onMovieChanged(movieModel)
    }
}