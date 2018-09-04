package com.themovielist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.themovielist.R
import com.themovielist.databinding.FragmentHomeBinding
import com.themovielist.model.response.Resource
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.ui.home.partiallist.HomePartialListFragment
import com.themovielist.util.extensions.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.include_appbar.view.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment: DaggerFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = viewModelProvider(viewModelFactory)

        val binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@HomeFragment)
            viewModel = homeViewModel
        }

        val mostRatedFragmentList = childFragmentManager.findFragmentById(R.id.homeRatedPartialListFragment) as HomePartialListFragment
        homeViewModel.moviesSortedByRating.observe(this, Observer { result ->
           handleResourceStatus(mostRatedFragmentList, result)

        })

        val mostPopularFragmentList = childFragmentManager.findFragmentById(R.id.homePopularPartialListFragment) as HomePartialListFragment
        homeViewModel.moviesSortedByPopularity.observe(this, Observer { result ->
            handleResourceStatus(mostPopularFragmentList, result)
        })

        binding.root.title.text = getString(R.string.home)

        return binding.root
    }

    private fun handleResourceStatus(fragmentList: HomePartialListFragment, result: Resource<List<MovieImageGenreViewModel>>) {
        when (result.status) {
            Status.SUCCESS -> fragmentList.showMovies(result.data!!)
            Status.ERROR -> {
                Timber.e(result.exception, "An error occurred while tried to get the movies")
                fragmentList.showErrorLoadingMovies()
            }
            Status.LOADING -> fragmentList.showLoadingIndicator()
        }
    }
}