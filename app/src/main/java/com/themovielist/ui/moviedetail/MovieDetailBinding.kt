package com.themovielist.ui.moviedetail

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.themovielist.GlideApp
import com.themovielist.R
import com.themovielist.model.GenreModel
import com.themovielist.extensions.yearFromCalendar
import timber.log.Timber
import java.util.*

@BindingAdapter("runtime")
fun runtimeAdapter(textView: TextView, runtime: Int) {
    Timber.d("RUNTIME: $runtime")
    val hours = runtime / 60
    val minutes = runtime % 60
    textView.text = String.format(textView.context.getString(R.string.movie_runtime_format), hours, minutes)
}

@BindingAdapter("voteAverage")
fun voteAverageAdapter(textView: TextView, voteAverage: Double) {
    textView.text = voteAverage.toString()
}

@BindingAdapter("genres")
fun genreAdapter(textView: TextView, genreList: List<GenreModel>?) {
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