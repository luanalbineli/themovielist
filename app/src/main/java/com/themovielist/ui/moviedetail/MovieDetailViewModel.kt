package com.themovielist.ui.moviedetail

import androidx.lifecycle.ViewModel
import com.themovielist.repository.movie.MovieRepository
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
        private val movieRepository: MovieRepository
): ViewModel(){

}