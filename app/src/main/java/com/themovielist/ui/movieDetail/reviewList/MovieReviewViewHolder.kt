package com.themovielist.ui.movieDetail.reviewList

import com.themovielist.databinding.ItemMovieReviewFixmeBinding
import com.themovielist.model.response.MovieReviewResponseModel
import com.themovielist.widget.recyclerview.BaseViewHolder

class MovieReviewViewHolder(
    private val binding: ItemMovieReviewFixmeBinding
) : BaseViewHolder(binding.root) {

    fun bind(reviewModel: MovieReviewResponseModel) {
        binding.reviewModel = reviewModel
        binding.executePendingBindings()
    }
}