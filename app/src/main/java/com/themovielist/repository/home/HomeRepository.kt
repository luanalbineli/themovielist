package com.themovielist.repository.home

import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.ConfigurationResponseModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Resource
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.RepositoryBase
import com.themovielist.repository.common.CommonRepository
import com.themovielist.repository.favorite.FavoriteRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class HomeRepository @Inject constructor(retrofit: Retrofit, private val commonRepository: CommonRepository, private val favoriteRepository: FavoriteRepository): RepositoryBase<IHomeService>(retrofit) {
    override val serviceInstanceType: Class<IHomeService>
        get() = IHomeService::class.java

    fun getMoviesSortedByPopularity(pageIndex: Int) =
            getMoviesWithGenreAndConfiguration(mApiInstance.getPopularList(pageIndex))

    fun getMoviesSortedByRating(pageIndex: Int) =
            getMoviesWithGenreAndConfiguration(mApiInstance.getTopRatedList(pageIndex))

    private fun getMoviesWithGenreAndConfiguration(movieRequest: Deferred<PaginatedArrayResponseModel<MovieModel>>): MutableLiveData<Resource<List<MovieImageGenreViewModel>>> {
        val result = MutableLiveData<Resource<List<MovieImageGenreViewModel>>>()
        result.value = Resource.loading()
        launch {
            try {
                val genreListRequest = commonRepository.getAllGenres()
                val favoriteMovies = favoriteRepository.getFavoriteMovieIds()
                val configurationRequest = commonRepository.getConfiguration()

                val list = processMovieWithGenreResult(movieRequest.await(), genreListRequest.await(), configurationRequest.await(), favoriteMovies.await())
                withContext(UI) { result.value = Resource.success(list) }
            } catch (exception: Exception) {
                withContext(UI) { result.value = Resource.error(exception) }
            }


        }

        return result

      /*  return Single.zip(
                configurationRequest,
                movieRequest,
                genreListRequest,
                favoriteMovies,
                Function4 { configurationResponseModel: ConfigurationResponseModel,
                            upcomingMovieList: PaginatedArrayResponseModel<MovieModel>,
                            genreList: SparseArray<GenreModel>,
                            favoriteMovieIds: Array<Int> ->
                    val movieWithGenreModel = commonRepository.fillMovieGenresList(upcomingMovieList, genreList)
                    MovieListResponseModel(configurationResponseModel, movieWithGenreModel, genreList, favoriteMovieIds)
                })
                .map { movieListResponseModel ->
                    return@map movieListResponseModel.movieWithGenreList.results.map {
                        val genreList = commonRepository.fillMovieGenresList(it.movieModel, movieListResponseModel.genreListModel)
                        MovieImageGenreViewModel(genreList, it.movieModel, movieListResponseModel.favoriteMovieIds.contains(it.movieModel.id))
                    }
                }
                .observeOnMainThread()*/

    }

    private fun processMovieWithGenreResult(await: PaginatedArrayResponseModel<MovieModel>, genreMap: SparseArray<GenreModel>, await1: ConfigurationResponseModel, await2: Array<Int>): List<MovieImageGenreViewModel> {
        return await.results.map {
            val genreList = commonRepository.fillMovieGenresList(it, genreMap)
            MovieImageGenreViewModel(genreList, it, await2.contains(it.id))
        }
    }
}