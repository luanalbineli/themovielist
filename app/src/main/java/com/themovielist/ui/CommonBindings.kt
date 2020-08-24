package com.themovielist.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.extension.format
import com.themovielist.extension.setDisplay
import com.themovielist.extension.yearFromCalendar
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.response.genre.GenreResponseModel
import com.themovielist.model.view.MovieModel
import com.themovielist.util.ApiUtil
import com.themovielist.widget.text.ReadMoreOption
import timber.log.Timber
import java.util.*

@BindingAdapter(
    value = ["imageUrl", "imageSizeList", "viewWidth", "viewHeight"],
    requireAll = false
)
fun ImageView.movieImageUrl(
    imageUrl: String?,
    imageSizeList: Array<ApiImageSizeModel>,
    viewWidth: Float?,
    viewHeight: Float?
) {
    Timber.d("Binding the image into imageView: $imageUrl")
    if (imageUrl == null) {
        GlideApp.with(this)
            .load(AppCompatResources.getDrawable(context, R.drawable.ic_broken_image))
            .into(this)
    } else {
        val fullImageUrl = ApiUtil.buildPosterImageUrl(
            imageUrl,
            imageSizeList,
            viewWidth?.toInt() ?: Int.MAX_VALUE,
            viewHeight?.toInt() ?: Int.MAX_VALUE
        )
        Timber.d("Binding the image url: $fullImageUrl")
        GlideApp.with(this)
            .load(fullImageUrl)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(8)
                    )
                )
            )
            .into(this)
    }
}

@BindingAdapter("readMoreText")
fun readMoreText(textView: TextView, text: String) {
    val readMoreOption = ReadMoreOption.Builder(textView.context)
        .textLength(200, ReadMoreOption.TYPE_CHARACTER)
        .moreLabel(textView.context.getString(R.string.text_movie_detail_read_more))
        .lessLabel(textView.context.getString(R.string.text_movie_detail_read_less))
        .moreLabelColor(ContextCompat.getColor(textView.context, R.color.half_baked))
        .lessLabelColor(ContextCompat.getColor(textView.context, R.color.half_baked))
        .expandAnimation(true)
        .build()

    readMoreOption.addReadMoreTo(textView, text)
}

@BindingAdapter("android:text", "digits")
fun TextView.text(double: Double, digits: Int) {
    text = double.format(digits)
}

@BindingAdapter("movieYear")
fun TextView.movieYear(releaseDate: Date?) {
    text = releaseDate?.yearFromCalendar?.toString()
}

@BindingAdapter("genreList")
fun TextView.genreListBinding(genreList: List<GenreResponseModel>) {
    text = genreList.joinToString(separator = ", ") { it.name }
}

@BindingAdapter("movieMenu", "onFavoriteToggle", "onWatchToggle", requireAll = false)
fun AppCompatImageButton.movieMenu(
    movieModel: MovieModel,
    onFavoriteToggle: View.OnClickListener,
    onWatchToggle: View.OnClickListener
) {
    val contextThemeWrapper = ContextThemeWrapper(context, R.style.CustomPopupMenu)
    val popupMenu = PopupMenu(contextThemeWrapper, this)
    popupMenu.menuInflater.inflate(R.menu.movie_item, popupMenu.menu)
    popupMenu.menu[0].setTitle(
        if (movieModel.isFavorite)
            R.string.text_remove_favorite
        else
            R.string.text_set_favorite
    )

    popupMenu.menu[1].setTitle(
        if (movieModel.isWatched)
            R.string.text_remove_watched
        else
            R.string.text_set_watched
    )

    popupMenu.setOnMenuItemClickListener { item ->
        return@setOnMenuItemClickListener when (item.itemId) {
            R.id.menu_item_favorite -> {
                onFavoriteToggle.onClick(item.actionView)
                true
            }
            R.id.menu_item_watched -> {
                onWatchToggle.onClick(item.actionView)
                true
            }
            else -> {
                false
            }
        }
    }

    setOnClickListener { popupMenu.show() }
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.setDisplay(visible)
}