package com.themovielist.ui.horizontalMovieList

import androidx.hilt.lifecycle.ViewModelInject
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore
import com.themovielist.ui.base.MovieViewModel

class HorizontalMovieListViewModel
@ViewModelInject
constructor(
    val apiConfigurationFactory: ApiConfigurationFactory,
    movieRepository: MovieRepository,
    movieStore: MovieStore
) : MovieViewModel(movieRepository, movieStore) {

}