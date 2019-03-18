package com.themovielist.repository.home

import android.util.SparseArray
import androidx.lifecycle.LiveData
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.MovieMostPopularRatedListModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.repository.RepositoryBase
import com.themovielist.repository.common.CommonRepository
import com.themovielist.repository.favorite.FavoriteRepository
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import retrofit2.Retrofit
import javax.inject.Inject

class HomeRepository
@Inject
constructor(
        retrofit: Retrofit,
        private val commonRepository: CommonRepository,
        private val favoriteRepository: FavoriteRepository
): RepositoryBase<IHomeService>(retrofit) {
    override val serviceInstanceType: Class<IHomeService>
        get() = IHomeService::class.java

    fun getMostPopularAndMostRatedMovies(viewModelJob: Job): LiveData<Result<MovieMostPopularRatedListModel>> {
        return executeAsyncRequest(viewModelJob) {
            val mostRatedMovieList = apiInstance.getTopRatedList(ApiUtil.INITIAL_PAGE_INDEX).await()
            val mostPopularMovieList = apiInstance.getPopularList(ApiUtil.INITIAL_PAGE_INDEX).await()

            val genreListRequest = commonRepository.getAllGenres().await()
            val favoriteMovies = favoriteRepository.getFavoriteMovieIds().await()

            val fullMostRatedMovieList = createFullMovieModelList(mostRatedMovieList, genreListRequest, favoriteMovies)
            val fullMostPopularMovieList = createFullMovieModelList(mostPopularMovieList, genreListRequest, favoriteMovies)

            return@executeAsyncRequest MovieMostPopularRatedListModel(fullMostRatedMovieList, fullMostPopularMovieList)
        }
    }

    fun getMoviesSortedByPopularity(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(apiInstance.getPopularList(pageIndex), disposableParentJob)

    fun getMoviesSortedByRating(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(apiInstance.getTopRatedList(pageIndex), disposableParentJob)

    private fun getMoviesWithGenreAndConfiguration(movieRequest: Deferred<PaginatedArrayResponseModel<MovieModel>>, viewModelJob: Job): LiveData<Result<List<CompleteMovieModel>>> {
        return executeAsyncRequest(viewModelJob) {
            val genreListRequest = commonRepository.getAllGenres()
            val favoriteMovies = favoriteRepository.getFavoriteMovieIds()

            return@executeAsyncRequest createFullMovieModelList(movieRequest.await(), genreListRequest.await(), favoriteMovies.await())
        }
    }

    private fun createFullMovieModelList(movieList: PaginatedArrayResponseModel<MovieModel>, genreMap: SparseArray<GenreModel>, favoriteMovieMap: Map<Int, Boolean>): List<CompleteMovieModel> {
        return movieList.results.map { movieModel ->
            val genreList = commonRepository.fillMovieGenresList(movieModel, genreMap)
            val favoriteMovieInfo = favoriteMovieMap[movieModel.id]
            CompleteMovieModel(genreList, movieModel, favoriteMovieInfo != null, favoriteMovieInfo == true)
        }
    }
}