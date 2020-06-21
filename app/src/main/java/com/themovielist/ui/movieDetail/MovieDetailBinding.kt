package com.themovielist.ui.movieDetail

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.extension.yearFromCalendar
import com.themovielist.model.response.genre.GenreResponseModel
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

private const val URL_YOUTUBE_THUMBNAIL_FORMAT = "https://img.youtube.com/vi/%1\$s/0.jpg"

@BindingAdapter("trailerThumbnail")
fun trailerThumbnail(imageView: ImageView, source: String) {
    val finalUrl = String.format(URL_YOUTUBE_THUMBNAIL_FORMAT, source)
    GlideApp.with(imageView)
            .load(finalUrl)
            .transforms(CenterCrop(), RoundedCorners(8))
            .into(imageView)
}