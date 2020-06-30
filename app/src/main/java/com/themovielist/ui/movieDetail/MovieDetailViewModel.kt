package com.themovielist.ui.movieDetail

import androidx.lifecycle.*
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.model.response.*
import com.themovielist.model.view.MovieDetailModel
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore
import com.themovielist.ui.base.MovieViewModel
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    val apiConfigurationFactory: ApiConfigurationFactory,
    private val movieRepository: MovieRepository,
    movieStore: MovieStore
) : MovieViewModel(movieRepository, movieStore) {
    private val mMovie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel>
        get() = mMovie

    private val mMovieDetail = MediatorLiveData<Result<MovieDetailModel>>()
    val movieDetail: LiveData<Result<MovieDetailModel>>
        get() = mMovieDetail

    private val mShowFullMovieTrailerList = MutableLiveData<List<MovieTrailerResponseModel>>()
    val showFullMovieTrailerList: LiveData<List<MovieTrailerResponseModel>>
        get() = mShowFullMovieTrailerList

    private val mShowFullMovieReviewList = MutableLiveData<PaginatedArrayResponseModel<MovieReviewResponseModel>>()
    val showFullMovieReviewList: LiveData<PaginatedArrayResponseModel<MovieReviewResponseModel>>
        get() = mShowFullMovieReviewList

    fun init(movieModel: MovieModel) {
        mMovie.postValue(movieModel)

        loadMovieDetail(movieModel.movieResponseModel.id)
    }

    fun tryLoadMovieDetailAgain() {
        mMovie.value?.let { movieModel ->
            loadMovieDetail(movieModel.movieResponseModel.id)
        }
    }

    fun toggleMovieFavorite() {
        movie.value?.let { movieModel -> toggleMovieFavorite(movieModel) }
    }

    fun toggleMovieWatched() {
        movie.value?.let { movieModel -> toggleMovieWatched(movieModel) }
    }

    override fun onMovieChanged(movieModel: MovieModel) {
        mMovie.postValue(movieModel)
    }

    fun showFullMovieTrailerList() {
        mMovieDetail.value?.data?.movieDetailResponseModel?.trailers?.trailerList?.let { trailerList ->
            mShowFullMovieTrailerList.postValue(trailerList)
        }
    }

    fun showFullMovieReviewList() {
        mMovieDetail.value?.data?.movieDetailResponseModel?.reviews?.let { reviewList ->
            mShowFullMovieReviewList.postValue(reviewList)
        }
    }

    private fun loadMovieDetail(movieId: Int) {
        mMovieDetail.addSource(movieRepository.getMovieDetail(viewModelScope, movieId)) {
            mMovieDetail.postValue(it)
        }
    }

}