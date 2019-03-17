package com.themovielist.repository.favorite

import android.content.Context
import com.themovielist.extensions.getBoolean
import com.themovielist.extensions.getInt
import com.themovielist.extensions.map
import com.themovielist.model.MovieModel
import com.themovielist.model.response.Result
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import timber.log.Timber
import java.sql.SQLDataException
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val context: Context) {
    fun getFavoriteMovieIds(): Deferred<Map<Int, Boolean>> {
        val completableDeferred = CompletableDeferred<Map<Int, Boolean>>()
        val contentResolver = context.contentResolver
        if (contentResolver == null) {
            completableDeferred.completeExceptionally(RuntimeException("Cannot get the ContentResolver"))
        } else {
            val cursor = contentResolver.query(MovieContract.MovieEntry.CONTENT_URI, arrayOf(MovieContract.MovieEntry._ID), null, null, null)
            if (cursor == null) {
                completableDeferred.completeExceptionally(SQLDataException("An internal error occurred. The cursor is null."))
            } else {
                cursor.use {
                    val map = cursor.map {
                        cursor.getInt(MovieContract.MovieEntry._ID) to cursor.getBoolean(MovieContract.MovieEntry.COLUMN_WATCHED)
                    }
                    completableDeferred.complete(map)
                }
            }
        }

        return completableDeferred
    }

    fun removeFavoriteMovie(movieModel: MovieModel): Result<Unit> {
        Timber.d("Trying to remove movie from favorite: $movieModel")
        val contentResolver = context.contentResolver
        if (contentResolver == null) {
            return Result.error(RuntimeException("Cannot get the ContentResolver"))
        } else {
            val numberOfRemovedItems = contentResolver.delete(MovieContract.MovieEntry.buildMovieWithId(movieModel.id), null, null)
            Timber.d("Number of removed movies: $numberOfRemovedItems")
            if (numberOfRemovedItems < 1) {
                return Result.error(SQLDataException("An internal error occurred."))
            }
        }

        return Result.success(Unit)
    }

    fun saveFavoriteMovie(movieModel: MovieModel): Result<Unit> {
        Timber.d("Trying to set movie as favorite: $movieModel")
        val contentResolver = context.contentResolver
        if (contentResolver == null) {
            return Result.error(RuntimeException("Cannot get the ContentResolver"))
        } else {
            val uri = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, movieModel.toContentValues())
            Timber.d("Result of the insertion: $uri")
            if (uri == null) {
                return Result.error(SQLDataException("An internal error occurred."))
            }
        }

        return Result.success(Unit)
    }
}