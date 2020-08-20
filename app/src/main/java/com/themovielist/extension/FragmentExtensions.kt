package com.themovielist.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.themovielist.R
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.ui.base.MovieViewModel
import com.themovielist.ui.movieDetail.MovieDetailActivity

fun Fragment.showSnackBarMessage(@StringRes messageResId: Int) =
    requireActivity().showSnackBarMessage(messageResId)

fun Fragment.showToggleMovieFavoriteMessage(movieModel: MovieModel) =
    requireActivity().showToggleMovieFavoriteMessage(movieModel)

fun Fragment.showToggleMovieWatchedMessage(movieModel: MovieModel) =
    requireActivity().showToggleMovieWatchedMessage(movieModel)

fun Fragment.handleMovieActions(movieViewModel: MovieViewModel) {
    movieViewModel.showMovieDetail.safeNullObserve(viewLifecycleOwner) {
        val intent = MovieDetailActivity.getIntent(requireActivity(), it)
        startActivity(intent)
    }

    movieViewModel.toggleMovieFavorite.safeNullObserve(viewLifecycleOwner) { result ->
        if (result.status == Status.SUCCESS) {
            showToggleMovieFavoriteMessage(result.data!!)
        } else if (result.status == Status.ERROR) {
            showSnackBarMessage(R.string.error_favorite_movie_text)
        }
    }

    movieViewModel.toggleMovieWatched.safeNullObserve(viewLifecycleOwner) { result ->
        if (result.status == Status.SUCCESS) {
            showToggleMovieWatchedMessage(result.data!!)
        } else if (result.status == Status.ERROR) {
            showSnackBarMessage(R.string.error_watched_movie_text)
        }
    }
}