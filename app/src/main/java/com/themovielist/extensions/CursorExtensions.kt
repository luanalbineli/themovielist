package com.themovielist.extensions

import android.database.Cursor

inline fun <reified T> Cursor.toArray(invoker: Cursor.() -> T): Array<T> {
    return Array(this.count) {
        this.moveToNext()
        invoker.invoke(this)
    }
}

inline fun <TKey, TValue> Cursor.map(invoker: Cursor.() -> Pair<TKey, TValue>): Map<TKey, TValue> {
    return HashMap<TKey, TValue>(this.count).also { hashMap ->
        this.moveToNext()
        val entry = invoker.invoke(this)
        hashMap[entry.first] = entry.second
    }
}

fun Cursor.getInt(columnName: String) = this.getInt(this.getColumnIndex(columnName))

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

fun Cursor.getBoolean(columnName: String): Boolean {
    val columnIndex = this.getColumnIndex(columnName)
    return this.getInt(columnIndex) == 1
}
