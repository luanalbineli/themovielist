package com.themovielist.ui.home.partiallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.databinding.RecyclerViewBinding
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.widget.recyclerview.HorizonalSpaceItemDecoration
import dagger.android.support.DaggerFragment

class HomePartialListFragment : DaggerFragment() {
    private val mAdapter by lazy { HomeMovieListAdapter(R.string.the_list_is_empty) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = RecyclerViewBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@HomePartialListFragment)
            recyclerView.adapter = mAdapter

            val dividerAmountOfSpace = inflater.context.resources.getDimension(R.dimen.home_movie_list_image_space)
            val spaceItemViewDecoration = HorizonalSpaceItemDecoration(dividerAmountOfSpace.toInt())
            recyclerView.addItemDecoration(spaceItemViewDecoration)

            val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = linearLayoutManager
        }

        return binding.root
    }

    fun showMovies(result: List<MovieImageGenreViewModel>) {
        mAdapter.hideLoadingIndicator()
        mAdapter.addItems(result)
    }

    fun showErrorLoadingMovies() {
        mAdapter.showErrorMessage()
    }

    fun showLoadingIndicator() {
        mAdapter.showLoading()
    }
}
