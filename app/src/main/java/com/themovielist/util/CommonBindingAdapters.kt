package com.themovielist.util

import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.model.response.ConfigurationImageResponseModel
import timber.log.Timber

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["imageUrl", "configurationImageResponseModel"], requireAll = true)
fun movieImageUrl(imageView: ImageView, imageUrl: String?, configurationImageResponseModel: ConfigurationImageResponseModel) {
    Timber.d("Binding the image into imageView: $imageUrl")
    if (imageUrl == null) {
        GlideApp.with(imageView)
                .load(AppCompatResources.getDrawable(imageView.context, R.drawable.ic_broken_image))
                .into(imageView)
    } else {
        Timber.d("The image url is not null. Width: ${imageView.width} - Height: ${imageView.height}")
        val fullImageUrl = ApiUtil.buildPosterImageUrl(imageUrl, configurationImageResponseModel, imageView.width, imageView.height)
        GlideApp.with(imageView)
                .load(fullImageUrl)
                .transforms(CenterCrop(), RoundedCorners(8))
                .into(imageView)
    }
}