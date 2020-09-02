package com.themovielist.ui.movieDetail.castList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.extension.setDisplay
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.response.credit.MovieCastResponseModel
import com.themovielist.widget.recyclerview.HorizonalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_horizontal_movie_list.*
import kotlinx.android.synthetic.main.list_view.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieCastListFragment : Fragment() {
    @Inject
    lateinit var apiConfigurationFactory: ApiConfigurationFactory

    private val mAdapter by lazy {
        MovieCastListAdapter(
            apiConfigurationFactory.apiConfigurationModel.posterImageSizes
        )
    }

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
    }

    fun setResult(result: Result<List<MovieCastResponseModel>>) {
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
