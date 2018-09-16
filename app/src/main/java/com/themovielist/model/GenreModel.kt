package com.themovielist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreModel(@SerializedName("id") val id: Int, @SerializedName("name") val name: String) : Parcelable