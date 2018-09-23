package com.themovielist.ui.moviedetail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.themovielist.R
import com.themovielist.model.GenreModel
import com.themovielist.util.extensions.yearFromCalendar
import java.util.*

@BindingAdapter("runtime")
fun runtimeAdapter(textView: TextView, runtime: Int) {
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