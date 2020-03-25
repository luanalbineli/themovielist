package com.themovielist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovielist.model.response.Result
import com.themovielist.model.view.HomeMovieListModel
import com.themovielist.repository.movie.MovieRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val movieRepository: MovieRepository
): ViewModel() {
    private val mHomeMovieList = MediatorLiveData<Result<HomeMovieListModel>>()
    val homeMovieList: LiveData<Result<HomeMovieListModel>>
        get() = mHomeMovieList

    init {
        fetchHomeMovieList()
    }

    private fun fetchHomeMovieList() {
        mHomeMovieList.addSource(movieRepository.getHomeMovieList(viewModelScope)) {
            mHomeMovieList.postValue(it)
        }
    }
}