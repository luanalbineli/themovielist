package com.themovielist.ui.home.horizontalList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.themovielist.R
import com.themovielist.extension.injector
import com.themovielist.extension.safeNullObserve
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.ui.home.HomeViewModel
import com.themovielist.ui.horizontalMovieList.HorizontalMovieListFragment

class HomeMovieListFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels(factoryProducer = { injector.homeViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val horizontalMovieListFragment =
            childFragmentManager.findFragmentByTag(getString(R.string.tag_movie_list)) as HorizontalMovieListFragment
        viewModel.homeMovieList.safeNullObserve(viewLifecycleOwner) { result ->
            val movieListResult = when (result.status) {
                Status.LOADING -> Result.loading()
                Status.SUCCESS -> Result.success(
                    if (tag == TAG_POPULAR) {
                        result.data!!.popularMovieList
                    } else {
                        result.data!!.topRatedMovieList
                    }
                )
                Status.ERROR -> Result.error(result.exception!!)
            }
            horizontalMovieListFragment.setResult(movieListResult)
        }
    }

    companion object {
        const val TAG_POPULAR = "popular"
        const val TAG_RATING = "rating"
    }
}
