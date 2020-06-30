package com.themovielist.ui.horizontalMovieList

import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore
import com.themovielist.ui.base.MovieViewModel
import javax.inject.Inject

class HorizontalMovieListViewModel
@Inject
constructor(
    val apiConfigurationFactory: ApiConfigurationFactory,
    movieRepository: MovieRepository,
    movieStore: MovieStore
): MovieViewModel(movieRepository, movieStore) {

}