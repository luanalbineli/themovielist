package com.themovielist.domain

import com.themovielist.model.MovieModel
import com.themovielist.repository.favorite.FavoriteRepository
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository): MediatorUseCase<MovieModel, FavoriteMovieResult>() {
    override fun execute(parameters: MovieModel) {

    }

}

data class FavoriteMovieResult constructor(val movieModel: MovieModel, val currentStatus: Boolean)