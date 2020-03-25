package com.themovielist.model.response.genre

import com.google.gson.annotations.SerializedName

data class GenreListResponseModel constructor(@SerializedName("genres") val genreList: List<GenreResponseModel>)
