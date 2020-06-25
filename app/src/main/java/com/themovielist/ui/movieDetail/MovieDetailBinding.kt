package com.themovielist.ui.movieDetail

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
fun TextView.genreAdapter(genreList: List<GenreResponseModel>?) {
    text = genreList?.asSequence()?.map { it.name }?.reduce { a, b -> "$a, $b" }
}

@BindingAdapter("yearFromCalendar")
fun TextView.yearFromDate(date: Date?) {
    text = date?.yearFromCalendar?.toString() ?: ""
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

@BindingAdapter("movieDetailTrailer")
fun View.movieDetailTrailer(source: String) {
   setOnClickListener {
       val uriString = "https://www.youtube.com/watch?v=$source"
       val uri = Uri.parse(String.format(uriString, source))
       val intent = Intent(Intent.ACTION_VIEW, uri)
       if (intent.resolveActivity(context.packageManager) != null) {
           context.startActivity(intent)
       } else {
           Toast.makeText(context, R.string.error_intent_cant_be_handled, Toast.LENGTH_SHORT).show()
       }
   }
}