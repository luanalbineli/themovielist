package com.themovielist.ui.movieList

import com.themovielist.databinding.ItemMovieVerticalBinding
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.view.MovieModel
import com.themovielist.ui.base.IMovieActions
import com.themovielist.widget.recyclerview.BaseViewHolder

class HomeMovieViewHolder(
    private val binding: ItemMovieVerticalBinding,
    private val movieActions: IMovieActions,
    private val apiImageSizeList: Array<ApiImageSizeModel>)
    : BaseViewHolder(binding.root) {

    fun bind(movieModel: MovieModel) {
        binding.model = movieModel
        binding.apiImageSizeList = apiImageSizeList
        binding.movieActions = movieActions
        binding.executePendingBindings()
    }
}