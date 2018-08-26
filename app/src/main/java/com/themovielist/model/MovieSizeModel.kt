package com.themovielist.model

import android.os.Parcel
import android.os.Parcelable

data class MovieSizeModel constructor(val size: Int, val sizeType: ImageSizeType) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            if (parcel.readInt() == ImageSizeType.HEIGHT.ordinal) ImageSizeType.HEIGHT else ImageSizeType.WIDTH)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(size)
        parcel.writeInt(sizeType.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieSizeModel> {
        override fun createFromParcel(parcel: Parcel): MovieSizeModel {
            return MovieSizeModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieSizeModel?> {
            return arrayOfNulls(size)
        }
    }

    enum class ImageSizeType {
        WIDTH,
        HEIGHT
    }
}