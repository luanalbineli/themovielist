package com.themovielist.ui.movieDetail

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.extension.yearFromCalendar
import com.themovielist.model.response.genre.GenreResponseModel
import timber.log.Timber
import java.util.*

@BindingAdapter("movieDetailRuntime")
fun TextView.movieDetailRuntime(runtime: Int?) {
    text = if (runtime == null) {
        null
    } else {
        val hours = runtime / 60
        val minutes = runtime % 60
        String.format(context.getString(R.string.format_movie_detail_runtime), hours, minutes)
    }
}

@BindingAdapter("genres")
fun genreAdapter(textView: TextView, genreList: List<GenreResponseModel>?) {
    textView.text = genreList?.asSequence()?.map { it.name }?.reduce { a, b -> "$a, $b" }
}

@BindingAdapter("yearFromCalendar")
fun yearFromDate(textView: TextView, date: Date?) {
    textView.text = date?.yearFromCalendar?.toString() ?: ""
}

@BindingAdapter("trailerThumbnail")
fun ImageView.trailerThumbnail(source: String) {
    val finalUrl = "https://img.youtube.com/vi/$source/0.jpg"
    GlideApp.with(this)
        .load(finalUrl)
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