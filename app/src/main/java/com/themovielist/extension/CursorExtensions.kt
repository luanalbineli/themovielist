package com.themovielist.extension

import android.database.Cursor

inline fun <reified T> Cursor.toArray(invoker: Cursor.() -> T): Array<T> {
    return Array(this.count) { _ ->
        this.moveToNext()
        invoker.invoke(this)
    }
}

fun Cursor.getIntArray(columnName: String): IntArray {
    val columnIndex = this.getColumnIndex(columnName)
    val stringContent = this.getString(columnIndex)
    return stringToIntArray(stringContent)
}

fun Cursor.getNullableString(columnName: String): String? {
    val columnIndex = this.getColumnIndex(columnName)
    if (this.isNull(columnIndex)) {
        return null
    }
    return this.getString(columnIndex)
}
