package com.themovielist.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.themovielist.MainApplication
import com.themovielist.R
import com.themovielist.model.view.MovieModel

val Fragment.injector get() = (requireActivity().application as MainApplication).component

fun Fragment.showSnackBarMessage(@StringRes messageResId: Int) = Snackbar.make(
    requireActivity().findViewById(android.R.id.content),
    messageResId, Snackbar.LENGTH_LONG
).show()

fun Fragment.showToggleMovieFavoriteMessage(movieModel: MovieModel) {
    val messageResId = if (movieModel.isFavorite)
        R.string.text_movie_added_favorite
    else
        R.string.text_movie_removed_favorite

    showSnackBarMessage(messageResId)
}

fun Fragment.showToggleMovieWatchedMessage(movieModel: MovieModel) {
    val messageResId = if (movieModel.isWatched)
        R.string.text_movie_added_watched
    else
        R.string.text_movie_removed_watched

    showSnackBarMessage(messageResId)
}