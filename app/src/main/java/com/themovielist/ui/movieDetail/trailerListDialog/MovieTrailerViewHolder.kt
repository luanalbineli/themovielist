package com.themovielist.ui.movieDetail.trailerListDialog

import com.themovielist.databinding.ItemMovieTrailerBinding
import com.themovielist.model.response.MovieTrailerResponseModel
import com.themovielist.widget.recyclerview.BaseViewHolder

class MovieTrailerViewHolder(
    private val binding: ItemMovieTrailerBinding
) : BaseViewHolder(binding.root) {

    fun bind(trailerModel: MovieTrailerResponseModel) {
        binding.trailerModel = trailerModel
        binding.executePendingBindings()
    }
}