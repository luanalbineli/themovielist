package com.themovielist.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.extension.injector
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels(factoryProducer = { injector.movieListViewModelFactory() })

    private val mAdapter by lazy {
        MovieListAdapter(
            apiPosterImageSizes = viewModel.apiConfigurationFactory.apiConfigurationModel.posterImageSizes,
            movieActions = viewModel
        )
    }

    var onLoadMoreItems: (() -> Unit)? = null

    private val mLinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list_movie.adapter = mAdapter
        list_movie.layoutManager = mLinearLayoutManager
        list_movie.onLoadMoreItems = onLoadMoreItems
        list_movie.addItemDecoration(DividerItemDecoration(requireContext(), mLinearLayoutManager.orientation))
    }

    fun setMovieListResult(result: Result<PaginatedArrayResponseModel<MovieModel>>) {
        when (result.status) {
            Status.LOADING -> mAdapter.showLoadingStatus()
            Status.ERROR -> mAdapter.showErrorStatus()
            Status.SUCCESS -> showMovieList(result.data!!)
        }
    }

    private fun showMovieList(result: PaginatedArrayResponseModel<MovieModel>) {
        mAdapter.submitList(result.results)
        if (result.hasMorePages()) {
            list_movie.enableLoadMoreItems(mLinearLayoutManager)
        } else {
            list_movie.disableLoadMoreItems()
            if (result.results.isEmpty()) {
                mAdapter.showEmptyStatus()
            }
        }
    }
}