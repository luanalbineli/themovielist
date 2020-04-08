package com.themovielist.repository.movie

import android.util.SparseArray
import androidx.core.util.contains
import androidx.lifecycle.LiveData
import com.themovielist.model.entity.MovieEntityModel
import com.themovielist.model.entity.MovieFavoriteWatchedEntityModel
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.response.genre.GenreResponseModel
import com.themovielist.model.view.HomeMovieListModel
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.base.Repository
import com.themovielist.repository.base.RepositoryExecutor
import com.themovielist.repository.common.CommonRepository
import com.themovielist.repository.favorite.RoomRepository
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MovieRepository @Inject
constructor(
    repositoryExecutor: RepositoryExecutor,
    private val commonRepository: CommonRepository,
    private val roomRepository: RoomRepository
) : Repository<IMovieService>(repositoryExecutor) {
    override val serviceInstanceType: Class<IMovieService>
        get() = IMovieService::class.java

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

    fun updateMovie(viewModelScope: CoroutineScope, movieModel: MovieModel): LiveData<Result<MovieModel>> {
        return makeRequest(viewModelScope) {
            roomRepository.updateMovie(movieModel)
            return@makeRequest movieModel
        }
    }

    private suspend fun createHomeMovieList(
        popularMovieList: List<MovieResponseModel>,
        topRatedMovieList: List<MovieResponseModel>
    ): HomeMovieListModel {
        val genreList = commonRepository.getAllGenres()
        val favoriteWatchedMovieList = roomRepository.getFavoriteWatchedMovieList()

        return HomeMovieListModel(
            popularMovieList.map {
                convertMovieResponseToMovie(
                    it,
                    genreList,
                    favoriteWatchedMovieList
                )
            },
            topRatedMovieList.map {
                convertMovieResponseToMovie(
                    it,
                    genreList,
                    favoriteWatchedMovieList
                )
            }
        )
    }

    private fun convertMovieResponseToMovie(
        movieResponseModel: MovieResponseModel,
        genreResponseList: SparseArray<GenreResponseModel>,
        favoriteWatchedMovieList: List<MovieFavoriteWatchedEntityModel>
    ): MovieModel {
        val movieFavoriteWatchedEntityModel =
            favoriteWatchedMovieList.firstOrNull { it.id == movieResponseModel.id }
        val genreList = movieResponseModel.genreIdList.flatMap { genreId ->
            if (genreResponseList.contains(genreId))
                listOf(genreResponseList[genreId])
            else
                emptyList()
        }

        return MovieModel(
            movieResponseModel = movieResponseModel,
            isFavorite = movieFavoriteWatchedEntityModel != null && movieFavoriteWatchedEntityModel.isFavorite,
            isWatched = movieFavoriteWatchedEntityModel != null && movieFavoriteWatchedEntityModel.isFavorite,
            genreList = genreList
        )
    }
}