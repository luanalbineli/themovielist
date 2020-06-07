package com.themovielist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.enums.HomeMovieSortType
import com.themovielist.model.response.Result
import com.themovielist.model.view.HomeMovieListModel
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.ui.base.MovieViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val movieRepository: MovieRepository,
        val apiConfigurationFactory: ApiConfigurationFactory
): MovieViewModel(movieRepository) {
    private val mHomeMovieList = MediatorLiveData<Result<HomeMovieListModel>>()
    val homeMovieList: LiveData<Result<HomeMovieListModel>>
        get() = mHomeMovieList

    private val mShowFullMovieList = MutableLiveData<HomeMovieSortType>()
    val showFullMovieList: LiveData<HomeMovieSortType>
        get() = mShowFullMovieList

    init {
        fetchHomeMovieList()
    }

    private fun fetchHomeMovieList() {
        mHomeMovieList.addSource(movieRepository.getHomeMovieList(viewModelScope)) {
            mHomeMovieList.value = it
        }
    }

    fun tryFetchHomeDataAgain() = fetchHomeMovieList()

    fun showFullListSortedByPopularity() {
        mShowFullMovieList.postValue(HomeMovieSortType.POPULARITY)
    }

    fun showFullListSortedByRating() {
        mShowFullMovieList.postValue(HomeMovieSortType.RATING)
    }
}