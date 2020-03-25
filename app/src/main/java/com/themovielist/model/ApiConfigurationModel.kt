package com.themovielist.model

import java.util.*

data class ApiConfigurationModel constructor(
        val backdropImageSizes: Array<ApiImageSizeModel> = arrayOf(
                ApiImageSizeModel(300, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(780, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(1280, ApiImageSizeModel.ImageSizeType.WIDTH)
        ),
        val posterImageSizes: Array<ApiImageSizeModel> = arrayOf(
                ApiImageSizeModel(92, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(154, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(185, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(342, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(500, ApiImageSizeModel.ImageSizeType.WIDTH),
                ApiImageSizeModel(780, ApiImageSizeModel.ImageSizeType.WIDTH)
        )
)

data class ApiImageSizeModel constructor(val size: Int, val sizeType: ImageSizeType) {
    enum class ImageSizeType {
        WIDTH,
        HEIGHT
    }
}


