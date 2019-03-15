package com.themovielist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.themovielist.R
import com.themovielist.databinding.FragmentHomeBinding
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.ui.common.Event
import com.themovielist.ui.home.partiallist.HomePartialListFragment
import com.themovielist.ui.moviedetail.MovieDetailActivity
import com.themovielist.util.extensions.activityViewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.include_appbar.view.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment: DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var apiConfigurationFactory: ApiConfigurationFactory

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = activityViewModelProvider(viewModelFactory)

        val binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }

        val mostRatedFragmentList = childFragmentManager.findFragmentById(R.id.homeRatedPartialListFragment) as HomePartialListFragment
        mostRatedFragmentList.tryAgainClickListener = { homeViewModel.fetchMovieSortedByRating() }
        homeViewModel.moviesSortedByRating.observe(this, Observer { result ->
            Timber.d("Update from most rated movies")
           handleResourceStatus(mostRatedFragmentList, result)

        })

        val mostPopularFragmentList = childFragmentManager.findFragmentById(R.id.homePopularPartialListFragment) as HomePartialListFragment
        mostPopularFragmentList.tryAgainClickListener = { homeViewModel.fetchMoviesSortedByPopularity() }
        homeViewModel.moviesSortedByPopularity.observe(this, Observer { result ->
            Timber.d("Update from popular movies")
            handleResourceStatus(mostPopularFragmentList, result)
        })

        homeViewModel.navigateToMovieDetailAction.observe(this, Observer {
            openMovieDetail(it)
        })

        binding.root.title.text = getString(R.string.home)

        return binding.root
    }

    private fun openMovieDetail(it: Event<MovieImageGenreViewModel>) {
        startActivity(MovieDetailActivity.getIntent(requireContext(), it.content))
    }

    private fun handleResourceStatus(fragmentList: HomePartialListFragment, result: Result<List<MovieImageGenreViewModel>>) {
        Timber.d("handleResourceStatus - result: ${result.status}")
        when (result.status) {
            Status.SUCCESS -> fragmentList.showMovies(result.data!!, apiConfigurationFactory.apiConfigurationModel.posterImageSizes)
            Status.ERROR -> {
                Timber.e(result.exception, "An error occurred while tried to get the movies")
                fragmentList.showErrorLoadingMovies()
            }
            Status.LOADING -> fragmentList.showLoadingIndicator()
        }
    }
}