package com.themovielist.repository.favorite

import android.annotation.SuppressLint
import android.content.Context
import com.themovielist.util.extensions.toArray
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import java.sql.SQLDataException
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val context: Context) {
    @SuppressLint("Recycle")
    fun getFavoriteMovieIds(): Deferred<Array<Int>> {
        val completableDeferred = CompletableDeferred<Array<Int>>()
        val contentResolver = context.contentResolver
        if (contentResolver == null) {
            completableDeferred.completeExceptionally(RuntimeException("Cannot get the ContentResolver"))
        } else {
            val cursor = contentResolver.query(MovieContract.MovieEntry.CONTENT_URI, arrayOf(MovieContract.MovieEntry._ID), null, null, null)
            if (cursor == null) {
                completableDeferred.completeExceptionally(SQLDataException("An internal error occurred. The cursor is null."))
            } else {
                cursor.use {
                    val result = cursor.toArray {
                        getInt(getColumnIndex(MovieContract.MovieEntry._ID))
                    }
                    completableDeferred.complete(result)
                }
            }
        }

        return completableDeferred
    }
}