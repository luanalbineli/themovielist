package com.themovielist.ui.movieDetail.castList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.themovielist.databinding.ItemMovieCastBinding
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.response.credit.MovieCastResponseModel
import com.themovielist.widget.recyclerview.BaseViewHolder
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter

class MovieCastListAdapter(
    private val apiPosterImageSizes: Array<ApiImageSizeModel>,
) : CustomRecyclerViewAdapter<MovieCastResponseModel, MovieCastViewHolder>(CAST_DIFF) {
    override fun onCreateItemViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MovieCastViewHolder {
        val binding = ItemMovieCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MovieCastViewHolder(binding, apiPosterImageSizes)
    }

    override fun onBindItemViewHolder(holder: MovieCastViewHolder, position: Int) {
        val movieCastResponseModel = getItem(position)
        holder.bind(movieCastResponseModel)
    }

    companion object {
        @JvmField
        val CAST_DIFF = object : DiffUtil.ItemCallback<MovieCastResponseModel>() {
            override fun areItemsTheSame(
                oldItem: MovieCastResponseModel,
                newItem: MovieCastResponseModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieCastResponseModel,
                newItem: MovieCastResponseModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class MovieCastViewHolder(
    private val binding: ItemMovieCastBinding,
    private val apiImageSizeList: Array<ApiImageSizeModel>
) : BaseViewHolder(binding.root) {

    fun bind(movieCastResponseModel: MovieCastResponseModel) {
        binding.movieCastResponseModel = movieCastResponseModel
        binding.apiImageSizeList = apiImageSizeList
        binding.executePendingBindings()
    }
}