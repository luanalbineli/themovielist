package com.themovielist.ui.home.partiallist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.themovielist.databinding.HomeMovieListItemBinding
import com.themovielist.model.response.ConfigurationImageResponseModel
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter
import com.themovielist.widget.recyclerview.CustomRecyclerViewHolder

internal class HomeMovieListAdapter(
        private val lifecycleOwner: LifecycleOwner
    ) : CustomRecyclerViewAdapter<MovieImageGenreViewModel, HomeMovieListVH>() {

    lateinit var configurationImageResponseModel: ConfigurationImageResponseModel

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): HomeMovieListVH {
        val binding = HomeMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeMovieListVH(binding, configurationImageResponseModel, lifecycleOwner)
    }

    override fun onBindItemViewHolder(holder: HomeMovieListVH, position: Int) {
        val movieImageViewModel = getItemByPosition(position)
        holder.bind(movieImageViewModel)
    }
}

class HomeMovieListVH(
        private val binding: HomeMovieListItemBinding,
        private val configurationImageResponseModel: ConfigurationImageResponseModel,
        private val lifecycleOwner: LifecycleOwner)
    : CustomRecyclerViewHolder(binding.root) {

    fun bind(movieImageViewModel: MovieImageGenreViewModel) {
        binding.model = movieImageViewModel
        binding.configurationImageModel = configurationImageResponseModel
        binding.setLifecycleOwner(lifecycleOwner)
        binding.executePendingBindings()
    }
}