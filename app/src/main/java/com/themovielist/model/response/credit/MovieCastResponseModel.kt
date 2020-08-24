package com.themovielist.model.response.credit

import com.google.gson.annotations.SerializedName

data class MovieCastResponseModel(
    @SerializedName("character")
    val character: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String?
)