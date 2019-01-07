package com.themovielist.repository.movie

import androidx.lifecycle.MutableLiveData
import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.Result
import com.themovielist.repository.RepositoryBase
import kotlinx.coroutines.Job
import retrofit2.Retrofit
import javax.inject.Inject

class MovieRepository @Inject constructor(retrofit: Retrofit): RepositoryBase<IMovieService>(retrofit) {
    override val serviceInstanceType: Class<IMovieService>
        get() = IMovieService::class.java

    fun getMovieDetail(movieId: Int, disposableParentJob: Job): MutableLiveData<Result<MovieDetailResponseModel>> {
        return executeAsyncRequest(disposableParentJob) {
            apiInstance.getMovieDetail(movieId).await()
        }

    }
}