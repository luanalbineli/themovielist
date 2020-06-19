package com.themovielist.repository.home

import androidx.lifecycle.LiveData
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.HomeMovieListModel
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.base.Repository
import com.themovielist.repository.base.RepositoryExecutor
import com.themovielist.repository.common.CommonRepository
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class HomeRepository @Inject constructor(
    repositoryExecutor: RepositoryExecutor,
    private val commonRepository: CommonRepository
) : Repository<IHomeService>(repositoryExecutor) {
    override val serviceInstanceType: Class<IHomeService>
        get() = IHomeService::class.java

    fun getHomeMovieList(viewModelScope: CoroutineScope): LiveData<Result<HomeMovieListModel>> {
        return makeRequest(viewModelScope) {
            val popularMovieList = serviceInstance.getPopularList(ApiUtil.INITIAL_PAGE_INDEX)
            val topRatedMovieList = serviceInstance.getTopRatedList(ApiUtil.INITIAL_PAGE_INDEX)

            return@makeRequest createHomeMovieList(
                popularMovieList.results,
                topRatedMovieList.results
            )
        }
    }

    fun getPopularList(
        viewModelScope: CoroutineScope,
        pageIndex: Int
    ): LiveData<Result<PaginatedArrayResponseModel<MovieModel>>> {
        return makeRequest(viewModelScope) {
            val result = serviceInstance.getPopularList(pageIndex)

            val movieList = commonRepository.mapToMovieList(result.results)
            return@makeRequest PaginatedArrayResponseModel(
                movieList,
                result.page,
                result.totalPages
            )
        }
    }

    fun getTopRatedList(
        viewModelScope: CoroutineScope,
        pageIndex: Int
    ): LiveData<Result<PaginatedArrayResponseModel<MovieModel>>> {
        return makeRequest(viewModelScope) {
            val result = serviceInstance.getTopRatedList(pageIndex)

            val movieList = commonRepository.mapToMovieList(result.results)
            return@makeRequest PaginatedArrayResponseModel(
                movieList,
                result.page,
                result.totalPages
            )
        }
    }

    private suspend fun createHomeMovieList(
        popularMovieList: List<MovieResponseModel>,
        topRatedMovieList: List<MovieResponseModel>
    ): HomeMovieListModel {
        return HomeMovieListModel(
            popularMovieList = commonRepository.mapToMovieList(popularMovieList),
            topRatedMovieList = commonRepository.mapToMovieList(topRatedMovieList)
        )
    }
}