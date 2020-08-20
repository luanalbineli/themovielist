package com.themovielist.extension

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.themovielist.R
import com.themovielist.model.view.MovieModel

fun FragmentActivity.showSnackBarMessage(@StringRes messageResId: Int) = Snackbar.make(
    findViewById(android.R.id.content),
    messageResId, Snackbar.LENGTH_LONG
).show()

fun FragmentActivity.showToggleMovieFavoriteMessage(movieModel: MovieModel) {
    val messageResId = if (movieModel.isFavorite)
        R.string.text_movie_added_favorite
    else
        R.string.text_movie_removed_favorite

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.showToggleMovieWatchedMessage(movieModel: MovieModel) {
    val messageResId = if (movieModel.isWatched)
        R.string.text_movie_added_watched
    else
        R.string.text_movie_removed_watched

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
}