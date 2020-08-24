package com.themovielist.ui.movieDetail.reviewList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.themovielist.databinding.ItemMovieReviewFixmeBinding
import com.themovielist.model.response.MovieReviewResponseModel
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter

class MovieReviewAdapter(
) : CustomRecyclerViewAdapter<MovieReviewResponseModel, MovieReviewViewHolder>(MOVIE_REVIEW_DIFF) {

    override fun onCreateItemViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MovieReviewViewHolder {
        val binding =
            ItemMovieReviewFixmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieReviewViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: MovieReviewViewHolder, position: Int) {
        val movieReviewResponseModel = getItem(position)
        holder.bind(movieReviewResponseModel)
    }

    companion object {
        @JvmField
        val MOVIE_REVIEW_DIFF = object : DiffUtil.ItemCallback<MovieReviewResponseModel>() {
            override fun areItemsTheSame(
                oldItem: MovieReviewResponseModel,
                newItem: MovieReviewResponseModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieReviewResponseModel,
                newItem: MovieReviewResponseModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}