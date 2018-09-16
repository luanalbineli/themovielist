package com.themovielist.ui.common

import androidx.lifecycle.LiveData
import com.themovielist.model.view.MovieImageGenreViewModel

interface MovieCommonAction {
    val navigateToMovieDetailAction: LiveData<Event<MovieImageGenreViewModel>>
    fun openMovieDetail(movieImageGenreViewModel: MovieImageGenreViewModel)
    fun onHeartClicked(movieImageGenreViewModel: MovieImageGenreViewModel)
}