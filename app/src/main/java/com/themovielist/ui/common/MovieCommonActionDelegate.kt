package com.themovielist.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovielist.domain.FavoriteMovieParams
import com.themovielist.domain.FavoriteMovieUseCase
import com.themovielist.model.view.MovieImageGenreViewModel
import timber.log.Timber

class MovieCommonActionDelegate constructor(private val favoriteMovieUseCase: FavoriteMovieUseCase): MovieCommonAction {
    private val _navigateToMovieDetailAction = MutableLiveData<Event<MovieImageGenreViewModel>>()
    override val navigateToMovieDetailAction: LiveData<Event<MovieImageGenreViewModel>>
        get() = _navigateToMovieDetailAction

    override fun openMovieDetail(movieImageGenreViewModel: MovieImageGenreViewModel) {
        Timber.d("Open movie detail: ${movieImageGenreViewModel.movieModel.title}")
        _navigateToMovieDetailAction.postValue(Event(movieImageGenreViewModel))
    }

    override fun onHeartClicked(movieImageGenreViewModel: MovieImageGenreViewModel) {
        val isFavorite = movieImageGenreViewModel.isFavorite.not()
        favoriteMovieUseCase.execute(FavoriteMovieParams(movieImageGenreViewModel.movieModel, isFavorite))
    }
}