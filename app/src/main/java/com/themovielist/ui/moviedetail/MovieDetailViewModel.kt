package com.themovielist.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.themovielist.model.response.*
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.ui.base.BaseViewModel
import com.themovielist.ui.common.MovieCommonAction
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
        private val movieRepository: MovieRepository,
        private val movieCommonAction: MovieCommonAction
): BaseViewModel(), MovieCommonAction by movieCommonAction, Observer<Result<MovieDetailResponseModel>> {
    private val _trailerList = MutableLiveData<List<MovieTrailerModel>>()
    val trailerList: LiveData<List<MovieTrailerModel>>
        get() = _trailerList

    private val _reviewList = MutableLiveData<List<MovieReviewModel>>()
    val reviewList: LiveData<List<MovieReviewModel>>
        get() = _reviewList

    private val _runtime = MutableLiveData<Int>()
    val runtime: LiveData<Int>
        get() = _runtime

    override fun onChanged(result: Result<MovieDetailResponseModel>) {
        // TODO: FIX
        if (result.status == Status.SUCCESS) {
            val movieDetailResponseModel = result.data!!
            _trailerList.postValue(movieDetailResponseModel.trailerResponseModel.trailerList)
            _reviewList.postValue(movieDetailResponseModel.reviewsResponseModel.results)
            _runtime.postValue(movieDetailResponseModel.runtime)
        }
    }

    fun setMovieId(movieId: Int) {
        movieRepository.getMovieDetail(movieId, viewModelJob).observeForever(this)
    }

    fun showAllMovieTrailers() {

    }

    fun showAllMovieReviews() {

    }
}