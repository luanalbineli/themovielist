package com.themovielist.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.Resource
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.util.ApiUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class HomeViewModel @Inject constructor(homeRepository: HomeRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    val moviesSortedByRating = homeRepository.getMoviesSortedByRating(ApiUtil.INITIAL_PAGE_INDEX)
    val moviesSortedByPopularity = homeRepository.getMoviesSortedByPopularity(ApiUtil.INITIAL_PAGE_INDEX)

    private val compositeDisposable = CompositeDisposable()

    init {
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}