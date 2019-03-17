package com.themovielist.ui.home.partiallist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.themovielist.databinding.HomeMovieListItemBinding
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter
import com.themovielist.widget.recyclerview.CustomViewHolder

internal class HomeMovieListAdapter(
        private val lifecycleOwner: LifecycleOwner,
        private val eventListener: MovieCommonAction
    ) : CustomRecyclerViewAdapter<CompleteMovieModel, HomeMovieListVH>(CompleteMovieModelDiff.Diff) {

    lateinit var apiImageSizeList: Array<ApiImageSizeModel>

    override fun onCreateItemViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): HomeMovieListVH {
        val binding = HomeMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeMovieListVH(binding, apiImageSizeList, lifecycleOwner)
    }

    override fun onBindItemViewHolder(holder: HomeMovieListVH, position: Int) {
        val movieImageViewModel = getItem(position)
        holder.bind(movieImageViewModel, eventListener)
    }
}

class HomeMovieListVH(
        private val binding: HomeMovieListItemBinding,
        private val apiImageSizeList: Array<ApiImageSizeModel>,
        private val lifecycleOwner: LifecycleOwner)
    : CustomViewHolder(binding.root) {

    fun bind(movieImageViewModel: CompleteMovieModel, eventListener: MovieCommonAction) {
        binding.model = movieImageViewModel
        binding.apiImageSizeList = apiImageSizeList
        binding.eventListener = eventListener
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}