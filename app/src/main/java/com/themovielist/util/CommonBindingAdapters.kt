package com.themovielist.util

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.model.ApiImageSizeModel
import timber.log.Timber

/*@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}*/

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
