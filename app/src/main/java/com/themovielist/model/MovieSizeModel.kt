package com.themovielist.model

data class MovieSizeModel constructor(val size: Int, val sizeType: ImageSizeType) {
    enum class ImageSizeType {
        WIDTH,
        HEIGHT
    }
}