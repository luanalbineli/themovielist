package com.themovielist.repository.movie

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
import com.themovielist.repository.home.IHomeService
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Retrofit
import javax.inject.Inject

class MovieRepository @Inject constructor(retrofit: Retrofit, private val commonRepository: CommonRepository, private val favoriteRepository: FavoriteRepository): RepositoryBase<IMovieService>(retrofit) {
    override val serviceInstanceType: Class<IMovieService>
        get() = IMovieService::class.java

    fun getMoviesSortedByPopularity(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(mApiInstance.getPopularList(pageIndex), disposableParentJob)

    fun getMoviesSortedByRating(pageIndex: Int, disposableParentJob: Job) =
            getMoviesWithGenreAndConfiguration(mApiInstance.getTopRatedList(pageIndex), disposableParentJob)

    private fun getMoviesWithGenreAndConfiguration(movieRequest: Deferred<PaginatedArrayResponseModel<MovieModel>>, disposableParentJob: Job): MutableLiveData<Result<List<MovieImageGenreViewModel>>> {
        val result = MutableLiveData<Result<List<MovieImageGenreViewModel>>>()
        result.value = Result.loading()
        launch(parent = disposableParentJob, context = IO) {
            try {
                val genreListRequest = commonRepository.getAllGenres()
                val favoriteMovies = favoriteRepository.getFavoriteMovieIds()

                val list = processMovieWithGenreResult(movieRequest.await(), genreListRequest.await(), favoriteMovies.await())
                withContext(UI) { result.value = Result.success(list) }
            } catch (exception: Exception) {
                withContext(UI) { result.value = Result.error(exception) }
            }
        }

        return result

    }

    private fun processMovieWithGenreResult(await: PaginatedArrayResponseModel<MovieModel>, genreMap: SparseArray<GenreModel>, favoriteMovieIdList: Array<Int>): List<MovieImageGenreViewModel> {
        return await.results.map {
            val genreList = commonRepository.fillMovieGenresList(it, genreMap)
            MovieImageGenreViewModel(genreList, it, favoriteMovieIdList.contains(it.id))
        }
    }
}