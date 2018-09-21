package com.themovielist.repository.home

import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.RepositoryBase
import com.themovielist.repository.common.CommonRepository
import com.themovielist.repository.favorite.FavoriteRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Retrofit
import javax.inject.Inject

class HomeRepository @Inject constructor(retrofit: Retrofit, private val commonRepository: CommonRepository, private val favoriteRepository: FavoriteRepository): RepositoryBase<IHomeService>(retrofit) {
    override val serviceInstanceType: Class<IHomeService>
        get() = IHomeService::class.java

    fun getMoviesSortedByPopularity(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(apiInstance.getPopularList(pageIndex), disposableParentJob)

    fun getMoviesSortedByRating(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(apiInstance.getTopRatedList(pageIndex), disposableParentJob)

    private fun getMoviesWithGenreAndConfiguration(movieRequest: Deferred<PaginatedArrayResponseModel<MovieModel>>, parentDisposableJob: Job): MutableLiveData<Result<List<MovieImageGenreViewModel>>> {
        return executeAsyncRequest(parentDisposableJob) {
            val genreListRequest = commonRepository.getAllGenres()
            val favoriteMovies = favoriteRepository.getFavoriteMovieIds()

            return@executeAsyncRequest processMovieWithGenreResult(movieRequest.await(), genreListRequest.await(), favoriteMovies.await())
        }
    }

    private fun processMovieWithGenreResult(await: PaginatedArrayResponseModel<MovieModel>, genreMap: SparseArray<GenreModel>, favoriteMovieIdList: Array<Int>): List<MovieImageGenreViewModel> {
        return await.results.map {
            val genreList = commonRepository.fillMovieGenresList(it, genreMap)
            MovieImageGenreViewModel(genreList, it, favoriteMovieIdList.contains(it.id))
        }
    }
}