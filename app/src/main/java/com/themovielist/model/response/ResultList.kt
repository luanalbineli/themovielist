package com.themovielist.model.response

data class ResultList<out T>(val status: Status, val data: List<T>?, val exception: Throwable?) {
    companion object {
        fun <T> success(data: List<T>): Result<List<T>> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Throwable): Result<List<T>> {
            return Result(Status.ERROR, null, exception)
        }

        fun <T> loading(): Result<List<T>> {
            return Result(Status.LOADING, null, null)
        }
    }
}