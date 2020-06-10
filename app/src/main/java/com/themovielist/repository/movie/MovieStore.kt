package com.themovielist.repository.movie

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovielist.model.view.MovieModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieStore @Inject constructor() {
    private val mMovieChanged = ObservableField<MovieModel>()

    fun onMovieChanged(movieModel: MovieModel) {
        mMovieChanged.set(movieModel)
    }

    fun addObserver(observable: Observable.OnPropertyChangedCallback) {
        mMovieChanged.addOnPropertyChangedCallback(observable)
    }

    fun removeObserver(observable: Observable.OnPropertyChangedCallback) {
        mMovieChanged.addOnPropertyChangedCallback(observable)
    }
}