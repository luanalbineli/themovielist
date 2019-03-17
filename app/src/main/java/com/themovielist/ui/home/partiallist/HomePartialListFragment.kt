package com.themovielist.ui.home.partiallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.databinding.RecyclerViewBinding
import com.themovielist.di.AppComponent
import com.themovielist.extensions.activityViewModelProvider
import com.themovielist.model.ApiImageSizeModel
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.ui.base.BaseViewModelFragment
import com.themovielist.ui.home.HomeViewModel
import com.themovielist.widget.recyclerview.HorizonalSpaceItemDecoration

class HomePartialListFragment : BaseViewModelFragment<HomeViewModel>() {
    private lateinit var adapter: HomeMovieListAdapter

    /*var tryAgainClickListener: (() -> Unit)?
        get() { return adapter.tryAgainClickListener }
        set(value) {adapter.tryAgainClickListener = value}*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = HomeMovieListAdapter(this, viewModel)

        val binding = RecyclerViewBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)

            val dividerAmountOfSpace = inflater.context.resources.getDimension(R.dimen.home_movie_list_image_space)
            val spaceItemViewDecoration = HorizonalSpaceItemDecoration(dividerAmountOfSpace.toInt())
            recyclerView.addItemDecoration(spaceItemViewDecoration)

            val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = linearLayoutManager
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*favoriteMovieUseCase.observe().observe(this, Observer { toggleResult ->
            if (toggleResult.status == Status.SUCCESS) {
                val toggledMovieId = toggleResult.data!!.movieModel.id
                val movieIndex = adapter.items.indexOfFirst { it.movieModel.id == toggledMovieId  }
                if (movieIndex != -1) {
                    adapter.items[movieIndex].favorite = toggleResult.data.isFavorite
                    adapter.notifyItemChanged(movieIndex)
                }
            }
        })*/
    }

    fun showMovies(result: List<CompleteMovieModel>, apiImageSizeList: Array<ApiImageSizeModel>) {
        adapter.apiImageSizeList = apiImageSizeList
        /*adapter.hideLoadingIndicator()
        adapter.addItems(result)*/
    }

    fun showErrorLoadingMovies() {
        //adapter.showErrorMessage()
    }

    fun showLoadingIndicator() {
        //adapter.showLoading()
    }

    override fun instantiateViewModel(injector: AppComponent) = activityViewModelProvider(HomeViewModel::class.java, injector.homeViewModelFactory())
}
