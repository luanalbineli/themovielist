package com.themovielist.repository.home

import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.ConfigurationResponseModel
import com.themovielist.model.response.MovieListResponseModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.RepositoryBase
import com.themovielist.repository.common.CommonRepository
import com.themovielist.repository.favorite.FavoriteRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(retrofit: Retrofit, private val commonRepository: CommonRepository, private val favoriteRepository: FavoriteRepository): RepositoryBase<IHomeService>(retrofit) {
    override val serviceInstanceType: Class<IHomeService>
        get() = IHomeService::class.java

    fun getMoviesSortedByPopularity(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(mApiInstance.getPopularList(pageIndex), disposableParentJob)

    fun getMoviesSortedByRating(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(mApiInstance.getTopRatedList(pageIndex), disposableParentJob)

    private fun getMoviesWithGenreAndConfiguration(movieRequest: Deferred<PaginatedArrayResponseModel<MovieModel>>, disposableParentJob: Job): MutableLiveData<Result<MovieListResponseModel>> {
        Timber.d("GETTING THE MOVIES")
        val result = MutableLiveData<Result<MovieListResponseModel>>()
        result.value = Result.loading()
        launch(parent = disposableParentJob, context = IO) {
            try {
                val genreListRequest = commonRepository.getAllGenres()
                val favoriteMovies = favoriteRepository.getFavoriteMovieIds()
                val configurationRequest = commonRepository.getConfiguration()

                val list = processMovieWithGenreResult(movieRequest.await(), genreListRequest.await(), configurationRequest.await(), favoriteMovies.await())
                withContext(UI) { result.value = Result.success(list) }
            } catch (exception: Exception) {
                withContext(UI) { result.value = Result.error(exception) }
            }
        }

        return result

    }

    private fun processMovieWithGenreResult(await: PaginatedArrayResponseModel<MovieModel>, genreMap: SparseArray<GenreModel>, await1: ConfigurationResponseModel, await2: Array<Int>): MovieListResponseModel {
        val movieWithGenreList = await.results.map {
            val genreList = commonRepository.fillMovieGenresList(it, genreMap)
            MovieImageGenreViewModel(genreList, it, await2.contains(it.id))
        }

        return MovieListResponseModel(await1, movieWithGenreList)
    }
}