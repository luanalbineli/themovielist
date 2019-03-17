package com.themovielist.ui.common

import androidx.lifecycle.LiveData
import com.themovielist.model.view.CompleteMovieModel

interface MovieCommonAction {
    val navigateToMovieDetailAction: LiveData<Event<CompleteMovieModel>>
    fun openMovieDetail(movieImageGenreViewModel: CompleteMovieModel)
    fun onHeartClicked(movieImageGenreViewModel: CompleteMovieModel)
}