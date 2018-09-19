package com.themovielist.util

import com.themovielist.model.ApiImageSizeModel
import timber.log.Timber

object ApiUtil {
    const val ORIGINAL_IMAGE_SIZE_NAME = "original"

    private const val BASE_URL_POSTER = "https://image.tmdb.org/t/p/"

    fun buildPosterImageUrl(posterKey: String, imageSizeList: Array<ApiImageSizeModel>, viewWidth: Int = Int.MAX_VALUE, viewHeight: Int = Int.MAX_VALUE): String {
        val imageSize = getApiImageSize(imageSizeList, viewWidth, viewHeight)
        return "$BASE_URL_POSTER$imageSize/${posterKey.removePrefix("/")}"
    }

    private fun getApiImageSize(apiImageSizeList: Array<ApiImageSizeModel>, posterWidth: Int = Int.MAX_VALUE, posterHeight: Int = Int.MAX_VALUE): String {
        Timber.d("getApiImageSize: width: $posterWidth - height: $posterHeight || $apiImageSizeList")
        val posterSize = apiImageSizeList.firstOrNull {
            if (it.sizeType == ApiImageSizeModel.ImageSizeType.WIDTH) {
                return@firstOrNull it.size >= posterWidth
            }

            return@firstOrNull it.size >= posterHeight
        } ?: return ORIGINAL_IMAGE_SIZE_NAME

        if (posterSize.sizeType == ApiImageSizeModel.ImageSizeType.WIDTH) {
            return "w${posterSize.size}"
        }
        return "h${posterSize.size}"
    }

    const val INITIAL_PAGE_INDEX = 1

}
