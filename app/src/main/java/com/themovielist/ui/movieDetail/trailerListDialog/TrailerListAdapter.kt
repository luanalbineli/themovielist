package com.themovielist.ui.movieDetail.trailerListDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.themovielist.databinding.ItemMovieTrailerBinding
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.response.MovieTrailerResponseModel
import com.themovielist.ui.base.IMovieActions
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter

class MovieTrailerAdapter(
) : CustomRecyclerViewAdapter<MovieTrailerResponseModel, MovieTrailerViewHolder>(MOVIE_TRAILER_DIFF) {

    override fun onCreateItemViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MovieTrailerViewHolder {
        val binding =
            ItemMovieTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTrailerViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: MovieTrailerViewHolder, position: Int) {
        val movieTrailerResponseModel = getItem(position)
        holder.bind(movieTrailerResponseModel)
    }

    companion object {
        @JvmField
        val MOVIE_TRAILER_DIFF = object : DiffUtil.ItemCallback<MovieTrailerResponseModel>() {
            override fun areItemsTheSame(
                oldItem: MovieTrailerResponseModel,
                newItem: MovieTrailerResponseModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieTrailerResponseModel,
                newItem: MovieTrailerResponseModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}