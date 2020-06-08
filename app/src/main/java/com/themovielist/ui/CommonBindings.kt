package com.themovielist.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sackcentury.shinebuttonlib.ShineButton
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieModel

/*
@BindingAdapter("isChecked")
fun ShineButton.isChecked(isChecked: Boolean) {
    setChecked(isChecked)
}*/

@BindingAdapter("verticalMovieListAdapter")
fun RecyclerView.verticalMovieListAdapter(movieListResult: Result<List<MovieModel>>) {
    val
}
