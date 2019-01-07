package com.themovielist.repository.common

import android.util.SparseArray
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import com.themovielist.repository.RepositoryBase
import com.themovielist.util.extensions.mapToListNotNull
import kotlinx.coroutines.*
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class CommonRepository
@Inject
constructor(retrofit: Retrofit) : RepositoryBase<ICommonMovieService>(retrofit) {
    @Synchronized
    fun getAllGenres(): Deferred<SparseArray<GenreModel>> {
        Timber.d("Getting the genre map")
        val completableDeferred = CompletableDeferred<SparseArray<GenreModel>>()
        if (GENRE_MAP != null) {
            Timber.d("The map is cached")
            completableDeferred.complete(GENRE_MAP!!)
        } else {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val genreResult = apiInstance.getAllGenres().await()
                    GENRE_MAP = SparseArray<GenreModel>().also { sparseArray ->
                        genreResult.genreList.forEach { genreModel -> sparseArray.put(genreModel.id, genreModel) }
                    }.also {
                        completableDeferred.complete(it)
                    }
                } catch (exception: Exception) {
                    completableDeferred.cancel(exception)
                }
            }
        }

        return completableDeferred
    }

    fun fillMovieGenresList(movieModel: MovieModel, genreMap: SparseArray<GenreModel>): List<GenreModel>? {
        return movieModel.genreIdList.mapToListNotNull { genreId ->
            if (genreMap.indexOfKey(genreId) > -1) genreMap.get(genreId) else null
        }
    }

    override val serviceInstanceType: Class<ICommonMovieService>
        get() = ICommonMovieService::class.java

    companion object {
        @JvmField
        var GENRE_MAP: SparseArray<GenreModel>? = null
    }
}