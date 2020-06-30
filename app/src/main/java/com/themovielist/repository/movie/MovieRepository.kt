package com.themovielist.repository.movie

import androidx.lifecycle.LiveData
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieDetailModel
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.base.Repository
import com.themovielist.repository.base.RepositoryExecutor
import com.themovielist.repository.common.CommonRepository
import com.themovielist.repository.favorite.RoomRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MovieRepository @Inject
constructor(
    repositoryExecutor: RepositoryExecutor,
    private val roomRepository: RoomRepository,
    private val commonRepository: CommonRepository
) : Repository<IMovieService>(repositoryExecutor) {
    override val serviceInstanceType: Class<IMovieService>
        get() = IMovieService::class.java

    fun updateMovie(
        viewModelScope: CoroutineScope,
        movieModel: MovieModel
    ): LiveData<Result<MovieModel>> {
        return makeRequest(viewModelScope) {
            roomRepository.updateMovie(movieModel)
            return@makeRequest movieModel
        }
    }

    fun getMovieDetail(
        viewModelScope: CoroutineScope,
        movieId: Int
    ): LiveData<Result<MovieDetailModel>> = makeRequest(viewModelScope) {
        val movieDetailResponseModel = serviceInstance.getMovieDetail(movieId)
        val movieResponseRecommendationList = serviceInstance.getMovieRecommendationList(movieId)
        val movieRecommendationList = commonRepository.mapToMovieList(movieResponseRecommendationList.results)

        return@makeRequest MovieDetailModel(
            movieDetailResponseModel,
            movieRecommendationList
        )
    }
}