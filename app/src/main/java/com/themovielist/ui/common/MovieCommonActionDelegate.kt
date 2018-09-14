package com.themovielist.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovielist.domain.FavoriteMovieUseCase
import com.themovielist.model.MovieModel
import timber.log.Timber

class MovieCommonActionDelegate constructor(private val favoriteMovieUseCase: FavoriteMovieUseCase): MovieCommonAction {
    private val _navigateToMovieDetailAction = MutableLiveData<Event<MovieModel>>()
    override val navigateToMovieDetailAction: LiveData<Event<MovieModel>>
        get() = _navigateToMovieDetailAction

    override fun openMovieDetail(movieModel: MovieModel) {
        _navigateToMovieDetailAction.postValue(Event(movieModel))
    }

    override fun onHeartClicked(movieModel: MovieModel) {
        Timber.d("Toggle movie favorite")
    }
}