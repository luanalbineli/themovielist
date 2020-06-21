package com.themovielist.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.themovielist.MainApplication
import com.themovielist.model.view.MovieModel

val Fragment.injector get() = (requireActivity().application as MainApplication).component

fun Fragment.showSnackBarMessage(@StringRes messageResId: Int) = requireActivity().showSnackBarMessage(messageResId)

fun Fragment.showToggleMovieFavoriteMessage(movieModel: MovieModel) = requireActivity().showToggleMovieFavoriteMessage(movieModel)

fun Fragment.showToggleMovieWatchedMessage(movieModel: MovieModel) = requireActivity().showToggleMovieWatchedMessage(movieModel)