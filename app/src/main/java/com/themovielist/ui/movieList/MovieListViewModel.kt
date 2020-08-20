package com.themovielist.ui.movieList

import androidx.hilt.lifecycle.ViewModelInject
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore
import com.themovielist.ui.base.MovieViewModel

class MovieListViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository,
    val apiConfigurationFactory: ApiConfigurationFactory,
    movieStore: MovieStore
) : MovieViewModel(movieRepository, movieStore)