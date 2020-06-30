package com.themovielist.ui.horizontalMovieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.extension.handleMovieActions
import com.themovielist.extension.injector
import com.themovielist.extension.setDisplay
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.widget.recyclerview.HorizonalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_horizontal_movie_list.*
import kotlinx.android.synthetic.main.list_view.*

class HorizontalMovieListFragment : Fragment() {
    private val mAdapter by lazy {
        HorizontalMovieListAdapter(
            viewModel.apiConfigurationFactory.apiConfigurationModel.posterImageSizes,
            viewModel
        )
    }

    private val viewModel: HorizontalMovieListViewModel by activityViewModels(factoryProducer = { injector.horizontalMovieListViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_horizontal_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list_view.adapter = mAdapter
        list_view.setHasFixedSize(true)

        val dividerAmountOfSpace = resources.getDimension(R.dimen.home_movie_list_image_space)
        val spaceItemViewDecoration = HorizonalSpaceItemDecoration(dividerAmountOfSpace.toInt())
        list_view.addItemDecoration(spaceItemViewDecoration)

        val linearLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        list_view.layoutManager = linearLayoutManager

        handleMovieActions(viewModel)
    }

    fun setResult(result: Result<List<MovieModel>>) {
        if (result.status == Status.LOADING) {
            toggleShimmerList(true)
        } else if (result.status == Status.SUCCESS) {
            mAdapter.submitList(result.data!!)
            toggleShimmerList(false)
        }
    }

    private fun toggleShimmerList(showShimmeringList: Boolean) {
        if (showShimmeringList) {
            shimmer_home_horizontal_movie_list.startShimmer()
        } else {
            shimmer_home_horizontal_movie_list.stopShimmer()
        }

        container_home_horizontal_movie_list_shimmer.setDisplay(showShimmeringList)
        list_view.setDisplay(!showShimmeringList)
    }
}
