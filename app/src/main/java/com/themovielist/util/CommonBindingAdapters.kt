package com.themovielist.util

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.model.ApiImageSizeModel
import timber.log.Timber
import com.themovielist.widget.ReadMoreOption

@BindingAdapter(value = ["imageUrl", "imageSizeList", "viewWidth", "viewHeight"], requireAll = false)
fun movieImageUrl(imageView: ImageView, imageUrl: String?, imageSizeList: Array<ApiImageSizeModel>, viewWidth: Float?, viewHeight: Float?) {
    Timber.d("Binding the image into imageView: $imageUrl")
    if (imageUrl == null) {
        GlideApp.with(imageView)
                .load(AppCompatResources.getDrawable(imageView.context, R.drawable.ic_broken_image))
                .into(imageView)
    } else {
        val fullImageUrl = ApiUtil.buildPosterImageUrl(imageUrl, imageSizeList, viewWidth?.toInt() ?: Int.MAX_VALUE, viewHeight?.toInt() ?: Int.MAX_VALUE)
        Timber.d("Binding the image url: $fullImageUrl")
        GlideApp.with(imageView)
                .load(fullImageUrl)
                .transforms(CenterCrop(), RoundedCorners(8))
                .into(imageView)
    }
}

@BindingAdapter("readMoreText")
fun readMoreText(textView: TextView, text: String) {
    val readMoreOption = ReadMoreOption.Builder(textView.context)
            .textLength(3, ReadMoreOption.TYPE_LINE)
            .moreLabel(textView.context.getString(R.string.read_more))
            .lessLabel(textView.context.getString(R.string.read_less))
            .moreLabelColor(textView.context.resources.getColor(R.color.half_baked))
            .lessLabelColor(textView.context.resources.getColor(R.color.half_baked))
            .expandAnimation(true)
            .build()

    readMoreOption.addReadMoreTo(textView, text)
}
