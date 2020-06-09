package com.themovielist.ui.movieList

import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.ui.base.MovieViewModel
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
        movieRepository: MovieRepository,
        val apiConfigurationFactory: ApiConfigurationFactory
): MovieViewModel(movieRepository) {

}