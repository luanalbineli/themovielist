package com.themovielist.ui.home.partiallist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.themovielist.databinding.HomeMovieListItemBinding
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.view.MovieModel
import com.themovielist.widget.recyclerview.BaseViewHolder
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter

class HorizontalMovieListAdapter(
    ) : CustomRecyclerViewAdapter<MovieModel, HomeMovieListVH>(MOVIE_DIFF) {

    lateinit var apiImageSizeList: Array<ApiImageSizeModel>

    override fun onCreateItemViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): HomeMovieListVH {
        val binding = HomeMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeMovieListVH(binding, apiImageSizeList)
    }


    override fun onBindItemViewHolder(holder: HomeMovieListVH, position: Int) {
        val movieImageViewModel = getItem(position)
        holder.bind(movieImageViewModel)
    }

    companion object {
        @JvmField
        val MOVIE_DIFF = object: DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.movieResponseModel.id == newItem.movieResponseModel.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.movieResponseModel.id == newItem.movieResponseModel.id &&
                        oldItem.isFavorite == newItem.isFavorite &&
                        oldItem.isWatched == newItem.isWatched
            }

        }
    }
}

class HomeMovieListVH(
        private val binding: HomeMovieListItemBinding,
        private val apiImageSizeList: Array<ApiImageSizeModel>)
    : BaseViewHolder(binding.root) {

    fun bind(movieModel: MovieModel) {
        binding.model = movieModel
        binding.apiImageSizeList = apiImageSizeList
        binding.executePendingBindings()
    }
}