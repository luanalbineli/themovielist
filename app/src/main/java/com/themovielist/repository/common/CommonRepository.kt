package com.themovielist.repository.common

import android.util.SparseArray
import com.themovielist.model.response.genre.GenreResponseModel
import com.themovielist.repository.base.Repository
import com.themovielist.repository.base.RepositoryExecutor
import timber.log.Timber
import javax.inject.Inject

class CommonRepository
@Inject
constructor(repositoryExecutor: RepositoryExecutor) : Repository<ICommonMovieService>(repositoryExecutor) {
    @Synchronized
    suspend fun getAllGenres(): SparseArray<GenreResponseModel> {
        Timber.d("Getting the genre map")
        if (GENRE_MAP == null) {
            val genreResult = serviceInstance.getAllGenres()
            GENRE_MAP = SparseArray<GenreResponseModel>().also { sparseArray ->
                genreResult.genreList.forEach { genreModel -> sparseArray.put(genreModel.id, genreModel) }
            }
        }

        return GENRE_MAP!!
    }

    override val serviceInstanceType: Class<ICommonMovieService>
        get() = ICommonMovieService::class.java

    companion object {
        @JvmField
        var GENRE_MAP: SparseArray<GenreResponseModel>? = null
    }
}