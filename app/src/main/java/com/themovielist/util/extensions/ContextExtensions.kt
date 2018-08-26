package com.themovielist.util.extensions

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.util.TypedValue
import io.reactivex.SingleEmitter
import java.sql.SQLDataException

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

inline fun <T> Context.tryQueryOnContentResolver(emitter: SingleEmitter<T>, cursorInvoker: ContentResolver.() -> Cursor?, safeFunction: Cursor.() -> Unit) {
    val contentResolver = contentResolver
    if (contentResolver == null) {
        emitter.onError(RuntimeException("Cannot get the ContentResolver"))
        return
    }

    val cursor = cursorInvoker.invoke(contentResolver)
    if (cursor == null) {
        emitter.onError(SQLDataException("An internal error occurred."))
        return
    }

    cursor.tryExecute(emitter, safeFunction)
}