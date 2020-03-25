package com.themovielist.ui.home.partiallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.extension.injector
import com.themovielist.extension.safeNullObserve
import com.themovielist.model.response.Status
import com.themovielist.model.view.HomeMovieListModel
import com.themovielist.ui.home.HomeViewModel
import com.themovielist.widget.recyclerview.HorizonalSpaceItemDecoration
import kotlinx.android.synthetic.main.list_view.*

class HorizontalMovieListFragment : Fragment() {
    private val mAdapter by lazy {
        HorizontalMovieListAdapter()
    }

    private val viewModel: HomeViewModel by activityViewModels(factoryProducer = { injector.homeViewModelFactory() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.list_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list_view.adapter = mAdapter
        list_view.setHasFixedSize(true)

        val dividerAmountOfSpace = resources.getDimension(R.dimen.home_movie_list_image_space)
        val spaceItemViewDecoration = HorizonalSpaceItemDecoration(dividerAmountOfSpace.toInt())
        list_view.addItemDecoration(spaceItemViewDecoration)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        list_view.layoutManager = linearLayoutManager

        viewModel.homeMovieList.safeNullObserve(viewLifecycleOwner) { result ->
            if (result.status == Status.LOADING) {
                mAdapter.showLoadingStatus()
            } else if (result.status == Status.SUCCESS) {
                showListResult(result.data!!)
            }
        }
    }

    private fun showListResult(homeMovieListModel: HomeMovieListModel) {
        val movieList = if (tag == TAG_POPULAR) {
            homeMovieListModel.popularMovieList
        } else {
            homeMovieListModel.popularMovieList
        }

        mAdapter.submitList(movieList)
    }

    companion object {
        const val TAG_POPULAR = "popular"
        const val TAG_RATING = "rating"
    }
}
