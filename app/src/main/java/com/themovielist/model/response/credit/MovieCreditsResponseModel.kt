package com.themovielist.model.response.credit

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponseModel(
    @SerializedName("cast")
    val castList: List<MovieCastResponseModel>
)