package com.themovielist.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovielist.domain.FavoriteMovieParams
import com.themovielist.domain.FavoriteMovieUseCase
import com.themovielist.model.view.CompleteMovieModel
import timber.log.Timber
import javax.inject.Inject

class MovieCommonActionDelegate @Inject constructor(private val favoriteMovieUseCase: FavoriteMovieUseCase): MovieCommonAction {
    private val _navigateToMovieDetailAction = MutableLiveData<Event<CompleteMovieModel>>()
    override val navigateToMovieDetailAction: LiveData<Event<CompleteMovieModel>>
        get() = _navigateToMovieDetailAction

    override fun openMovieDetail(movieImageGenreViewModel: CompleteMovieModel) {
        Timber.d("Open movie detail: ${movieImageGenreViewModel.movieModel.title}")
        _navigateToMovieDetailAction.postValue(Event(movieImageGenreViewModel))
    }

    override fun onHeartClicked(movieImageGenreViewModel: CompleteMovieModel) {
        val isFavorite = movieImageGenreViewModel.favorite.not()
        favoriteMovieUseCase.execute(FavoriteMovieParams(movieImageGenreViewModel.movieModel, isFavorite))
    }
}