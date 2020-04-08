package com.themovielist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.themovielist.R
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.extension.injector
import com.themovielist.extension.safeNullObserve
import com.themovielist.extension.setDisplay
import com.themovielist.extension.showSnackBarMessage
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.ui.moviedetail.MovieDetailActivity
import com.themovielist.widget.RequestStatusView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_appbar.*
import kotlinx.android.synthetic.main.item_home_horizontal_list.*
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var apiConfigurationFactory: ApiConfigurationFactory

    private val viewModel: HomeViewModel by activityViewModels(factoryProducer = { injector.homeViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        text_app_bar_title.setText(R.string.text_home)

        request_status_home.toggleStatus(RequestStatusView.Status.ERROR)
        request_status_home.onTryAgain = {
            viewModel.tryFetchHomeDataAgain()
        }

        viewModel.homeMovieList.safeNullObserve(viewLifecycleOwner) {
            container_home.setDisplay(it.status != Status.ERROR)
            request_status_home.setDisplay(it.status == Status.ERROR)
        }

        viewModel.showMovieDetail.safeNullObserve(this) {
            val intent = MovieDetailActivity.getIntent(requireActivity(), it)
            startActivity(intent)
        }

        viewModel.toggleMovieFavorite.safeNullObserve(this) { result ->
            if (result.status == Status.SUCCESS) {
                showToggleMovieFavoriteMessage(result.data!!)
            } else if (result.status == Status.ERROR) {
                showSnackBarMessage(R.string.error_favorite_)
            }
        }
    }

    private fun showToggleMovieFavoriteMessage(movieModel: MovieModel) {
        val messageResId = if (movieModel.isFavorite)
            R.string.text_movie_favorited
        else
            R.string.text_movie_favorited

        showSnackBarMessage(messageResId)
    }
}