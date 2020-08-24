package com.themovielist.repository.common

import android.util.SparseArray
import androidx.core.util.containsKey
import com.themovielist.model.entity.MovieFavoriteWatchedEntityModel
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.genre.GenreResponseModel
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.base.Repository
import com.themovielist.repository.base.RepositoryExecutor
import com.themovielist.repository.favorite.RoomRepository
import timber.log.Timber
import javax.inject.Inject

class CommonRepository
@Inject
constructor(
    repositoryExecutor: RepositoryExecutor,
    private val roomRepository: RoomRepository
) :
    Repository<ICommonMovieService>(repositoryExecutor) {
    override val serviceInstanceType: Class<ICommonMovieService>
        get() = ICommonMovieService::class.java

    @Synchronized
    suspend fun getAllGenres(): SparseArray<GenreResponseModel> {
        Timber.d("Getting the genre map")
        if (GENRE_MAP == null) {
            val genreResult = serviceInstance.getAllGenres()
            GENRE_MAP = SparseArray<GenreResponseModel>().also { sparseArray ->
                genreResult.genreList.forEach { genreModel ->
                    sparseArray.put(
                        genreModel.id,
                        genreModel
                    )
                }
            }
        }

        return GENRE_MAP!!
    }

    suspend fun mapToMovieList(movieResponseList: List<MovieResponseModel>): List<MovieModel> {
        val genreList = getAllGenres()
        val favoriteWatchedMovieList = roomRepository.getFavoriteWatchedMovieList()
        return movieResponseList.map {
            convertMovieResponseToMovie(
                it,
                genreList,
                favoriteWatchedMovieList
            )
        }

    }

    private fun convertMovieResponseToMovie(
        movieResponseModel: MovieResponseModel,
        genreResponseList: SparseArray<GenreResponseModel>,
        favoriteWatchedMovieList: List<MovieFavoriteWatchedEntityModel>
    ): MovieModel {
        val movieFavoriteWatchedEntityModel =
            favoriteWatchedMovieList.firstOrNull { it.id == movieResponseModel.id }
        val genreList = movieResponseModel.genreIdList.flatMap { genreId ->
            if (genreResponseList.containsKey(genreId))
                listOf(genreResponseList[genreId])
            else
                emptyList()
        }

        return MovieModel(
            movieResponseModel = movieResponseModel,
            isFavorite = movieFavoriteWatchedEntityModel != null && movieFavoriteWatchedEntityModel.isFavorite,
            isWatched = movieFavoriteWatchedEntityModel != null && movieFavoriteWatchedEntityModel.isWatched,
            genreList = genreList
        )
    }

    companion object {
        @JvmField
        var GENRE_MAP: SparseArray<GenreResponseModel>? = null
    }
}