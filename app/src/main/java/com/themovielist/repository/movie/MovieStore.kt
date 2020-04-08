package com.themovielist.repository.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovielist.model.view.MovieModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieStore @Inject constructor() {
    private val mMovieChanged = MutableLiveData<MovieModel>()
    val movieChanged: LiveData<MovieModel>
        get() = mMovieChanged

    fun onMovieChanged(movieModel: MovieModel) {
        mMovieChanged.postValue(movieModel)
    }
}