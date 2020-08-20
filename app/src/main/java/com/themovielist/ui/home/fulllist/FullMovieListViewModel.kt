package com.themovielist.ui.home.fulllist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.themovielist.enums.HomeMovieSortType
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore
import com.themovielist.ui.base.MovieViewModel
import com.themovielist.util.ApiUtil

class FullMovieListViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository,
    private val homeRepository: HomeRepository,
    movieStore: MovieStore
) : MovieViewModel(movieRepository, movieStore) {
    private val mMovieList = MediatorLiveData<Result<PaginatedArrayResponseModel<MovieModel>>>()
    val movieList: LiveData<Result<PaginatedArrayResponseModel<MovieModel>>>
        get() = mMovieList

    private var mNextPageIndex = ApiUtil.INITIAL_PAGE_INDEX
    private lateinit var homeMovieSortType: HomeMovieSortType

    fun init(homeMovieSortType: HomeMovieSortType) {
        this.homeMovieSortType = homeMovieSortType

        fetchMovieList()
    }

    fun loadMoreMovies() = fetchMovieList()

    private fun fetchMovieList() {
        val fullMovieList = movieList.value?.data?.results?.toMutableList() ?: mutableListOf()
        val source = if (homeMovieSortType == HomeMovieSortType.POPULARITY) {
            homeRepository.getPopularList(viewModelScope, mNextPageIndex)
        } else
            homeRepository.getTopRatedList(viewModelScope, mNextPageIndex)

        mMovieList.addSource(source) { result ->
            if (result.status == Status.SUCCESS) {
                val resultData = result.data!!
                fullMovieList.addAll(resultData.results)
                mMovieList.postValue(Result.success(resultData.copy(results = fullMovieList)))

                mNextPageIndex += 1
            } else {
                mMovieList.postValue(result)
            }
        }
    }

    fun tryFetchHomeDataAgain() = fetchMovieList()

    override fun onMovieChanged(movieModel: MovieModel) {
        movieList.value?.data?.let { result ->
            val updatedMovieList = updateMovieList(result.results, movieModel)
            if (updatedMovieList != null) {
                mMovieList.postValue(Result.success(result.copy(results = updatedMovieList)))
            }
        }

    }
}