package com.themovielist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.themovielist.R
import com.themovielist.databinding.FragmentHomeBinding
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.di.AppComponent
import com.themovielist.extensions.activityViewModelProvider
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.ui.base.BaseViewModelFragment
import com.themovielist.ui.common.Event
import com.themovielist.ui.home.partiallist.HomePartialListFragment
import com.themovielist.ui.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.include_appbar.view.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment: BaseViewModelFragment<HomeViewModel>() {
    override fun instantiateViewModel(injector: AppComponent)
            = activityViewModelProvider(HomeViewModel::class.java, injector.homeViewModelFactory())

    @Inject lateinit var apiConfigurationFactory: ApiConfigurationFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }

        val mostRatedFragmentList = childFragmentManager.findFragmentById(R.id.homeRatedPartialListFragment) as HomePartialListFragment
       /* mostRatedFragmentList.tryAgainClickListener = { viewModel.fetchMovieSortedByRating() }
        viewModel.moviesSortedByRating.observe(this, Observer { result ->
            Timber.d("Update from most rated movies")
           handleResourceStatus(mostRatedFragmentList, result)

        })*/

        val mostPopularFragmentList = childFragmentManager.findFragmentById(R.id.homePopularPartialListFragment) as HomePartialListFragment
        /*mostPopularFragmentList.tryAgainClickListener = { viewModel.fetchMoviesSortedByPopularity() }
        viewModel.moviesSortedByPopularity.observe(this, Observer { result ->
            Timber.d("Update from popular movies")
            handleResourceStatus(mostPopularFragmentList, result)
        })*/

        viewModel.navigateToMovieDetailAction.observe(this, Observer {
            openMovieDetail(it)
        })

        binding.root.title.text = getString(R.string.home)

        return binding.root
    }

    private fun openMovieDetail(it: Event<CompleteMovieModel>) {
        startActivity(MovieDetailActivity.getIntent(requireContext(), it.content))
    }

    private fun handleResourceStatus(fragmentList: HomePartialListFragment, result: Result<List<CompleteMovieModel>>) {
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

    companion object {
        fun getInstance() = HomeFragment()
    }
}