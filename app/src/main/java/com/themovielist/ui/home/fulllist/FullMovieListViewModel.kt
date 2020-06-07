package com.themovielist.ui.home.fulllist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.enums.HomeMovieSortType
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.ui.base.MovieViewModel
import com.themovielist.util.ApiUtil
import javax.inject.Inject

class FullMovieListViewModel @Inject constructor(
        private val movieRepository: MovieRepository,
        val apiConfigurationFactory: ApiConfigurationFactory
): MovieViewModel(movieRepository) {
    private val mMovieList = MediatorLiveData<Result<PaginatedArrayResponseModel<MovieModel>>>()
    val movieList: LiveData<Result<PaginatedArrayResponseModel<MovieModel>>>
        get() = mMovieList

    private var mCurrentPageIndex = ApiUtil.INITIAL_PAGE_INDEX
    private lateinit var homeMovieSortType: HomeMovieSortType

    fun init(homeMovieSortType: HomeMovieSortType) {
        this.homeMovieSortType = homeMovieSortType

        fetchMovieList()
    }

    private fun fetchMovieList() {
        val fullMovieList = movieList.value?.data?.results?.toMutableList() ?: mutableListOf()
        val pageIndex = mCurrentPageIndex + 1
        val source = if (homeMovieSortType == HomeMovieSortType.POPULARITY) {
            movieRepository.getPopularList(viewModelScope, pageIndex)
        } else
            movieRepository.getTopRatedList(viewModelScope, pageIndex)

        mMovieList.addSource(source) { result ->
            if (result.status == Status.SUCCESS) {
                val resultData = result.data!!
                fullMovieList.addAll(resultData.results)
                mMovieList.value = Result.success(PaginatedArrayResponseModel(results = fullMovieList, page = resultData.page, totalPages = resultData.totalPages))

                mCurrentPageIndex = pageIndex
            } else {
                mMovieList.value = result
            }
        }
    }

    fun tryFetchHomeDataAgain() = fetchMovieList()
}