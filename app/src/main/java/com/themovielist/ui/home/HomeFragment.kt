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
import com.themovielist.ui.home.partiallist.HomePartialListFragment
import com.themovielist.util.extensions.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_appbar.*
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
            Timber.d("The home movie list sorted by rating has changed")
            when (result.status) {
                Status.SUCCESS -> mostRatedFragmentList.showMovies(result.data!!)
                Status.ERROR -> {
                    Timber.e(result.exception, "An error occurred while tried to get the movies")
                    mostRatedFragmentList.showErrorLoadingMovies()
                }
                Status.LOADING -> mostRatedFragmentList.showLoadingIndicator()
            }

        })

        val mostPopularFragmentList = childFragmentManager.findFragmentById(R.id.homePopularPartialListFragment) as HomePartialListFragment

        homeViewModel.moviesSortedByPopularity.observe(this, Observer { result ->
            Timber.d("The home movie list sorted by popularity has changed")
            when (result.status) {
                Status.SUCCESS -> mostPopularFragmentList.showMovies(result.data!!)
                Status.ERROR -> {
                    Timber.e(result.exception, "An error occurred while tried to get the movies")
                    mostPopularFragmentList.showErrorLoadingMovies()
                }
                Status.LOADING -> mostPopularFragmentList.showLoadingIndicator()
            }
        })

        binding.root.title.text = "Home"

        return binding.root
    }
}