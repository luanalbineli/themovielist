package com.themovielist.model.response

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Result<out T>(val status: Status, val data: T?, val exception: Throwable?) {
    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Throwable): Result<T> {
            return Result(Status.ERROR, null, exception)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
