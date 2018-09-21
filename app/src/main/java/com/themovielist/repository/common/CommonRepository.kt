package com.themovielist.repository.common

import android.util.SparseArray
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.ConfigurationResponseModel
import com.themovielist.repository.RepositoryBase
import com.themovielist.util.extensions.mapToListNotNull
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch
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
            launch {
                val genreResult = apiInstance.getAllGenres().await()
                GENRE_MAP = SparseArray<GenreModel>().also { sparseArray ->
                    genreResult.genreList.forEach { genreModel -> sparseArray.put(genreModel.id, genreModel) }
                }.also {
                    completableDeferred.complete(it)
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

        @JvmField
        var GENRE_COLOR_MAP = SparseArray<String>()

        @JvmField
        var COLORS = arrayOf("#F44336", "#EC407A", "#AB47BC", "#4527A0",
                "#263238", "#000000", "#DD2C00", "#5D4037",
                "#757575", "#E65100", "#1B5E20", "#827717",
                "#0091EA", "#006064", "#6200EA", "#004D40",
                "#212121", "#FF1744", "#AA00FF", "#33691E",
                "#A1887F", "#C51162")
    }
}