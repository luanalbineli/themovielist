package com.themovielist.ui.common

import androidx.lifecycle.LiveData
import com.themovielist.model.MovieModel

interface MovieCommonAction {
    val navigateToMovieDetailAction: LiveData<Event<MovieModel>>
    fun openMovieDetail(movieModel: MovieModel)
    fun onHeartClicked(movieModel: MovieModel)
}