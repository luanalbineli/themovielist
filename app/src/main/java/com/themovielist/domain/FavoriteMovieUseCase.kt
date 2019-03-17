package com.themovielist.domain

import com.themovielist.model.MovieModel
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.repository.favorite.FavoriteRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMovieUseCase
@Inject
constructor(
        private val favoriteRepository: FavoriteRepository
) : MediatorUseCase<FavoriteMovieParams, FavoriteMovieResult>() {
    init {
        Timber.d("INSTANCE")
    }

    override fun execute(params: FavoriteMovieParams) {
        val updateResult = if (params.isFavorite) {
            favoriteRepository.saveFavoriteMovie(params.movieModel)
        } else {
            favoriteRepository.removeFavoriteMovie(params.movieModel)
        }
        Timber.d("Result: ${updateResult.status}")
        if (updateResult.status == Status.ERROR) {
            result.postValue(Result.error(updateResult.exception!!))
        } else {
            result.postValue(Result.success(FavoriteMovieResult(params.movieModel, params.isFavorite)))
        }
    }

}

data class FavoriteMovieParams constructor(val movieModel: MovieModel, val isFavorite: Boolean)

data class FavoriteMovieResult constructor(val movieModel: MovieModel, val isFavorite: Boolean)