package com.themovielist.model.response

import com.google.gson.annotations.SerializedName

data class PaginatedArrayResponseModel<T> constructor(
    @SerializedName("results")
    var results: List<T>,

    @SerializedName("page")
    val page: Int,

    @SerializedName("total_pages")
    val totalPages: Int
) {
    fun hasMorePages(): Boolean {
        return page < totalPages
    }
}
