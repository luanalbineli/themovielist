package com.themovielist.ui.movieDetail

import androidx.lifecycle.ViewModel
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieRepository
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    val apiConfigurationFactory: ApiConfigurationFactory
) : ViewModel() {
    lateinit var movieModel: MovieModel

    fun init(movieModel: MovieModel) {
        this.movieModel = movieModel
    }

}