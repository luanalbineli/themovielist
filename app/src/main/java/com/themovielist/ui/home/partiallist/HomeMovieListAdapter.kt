package com.themovielist.ui.home.partiallist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.themovielist.databinding.HomeMovieListItemBinding
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter
import com.themovielist.widget.recyclerview.CustomRecyclerViewHolder

internal class HomeMovieListAdapter(
        private val lifecycleOwner: LifecycleOwner,
        private val eventListener: MovieCommonAction
    ) : CustomRecyclerViewAdapter<MovieImageGenreViewModel, HomeMovieListVH>() {

    lateinit var apiImageSizeList: Array<ApiImageSizeModel>

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): HomeMovieListVH {
        val binding = HomeMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeMovieListVH(binding, apiImageSizeList, lifecycleOwner)
    }

    override fun onBindItemViewHolder(holder: HomeMovieListVH, position: Int) {
        val movieImageViewModel = getItemByPosition(position)
        holder.bind(movieImageViewModel, eventListener)
    }
}

class HomeMovieListVH(
        private val binding: HomeMovieListItemBinding,
        private val apiImageSizeList: Array<ApiImageSizeModel>,
        private val lifecycleOwner: LifecycleOwner)
    : CustomRecyclerViewHolder(binding.root) {

    fun bind(movieImageViewModel: MovieImageGenreViewModel, eventListener: MovieCommonAction) {
        binding.model = movieImageViewModel
        binding.apiImageSizeList = apiImageSizeList
        binding.eventListener = eventListener
        binding.setLifecycleOwner(lifecycleOwner)
        binding.executePendingBindings()
    }
}