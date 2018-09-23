package com.themovielist.ui.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.themovielist.R
import com.themovielist.databinding.ActivityMovieDetailBinding
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.model.response.MovieReviewModel
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.util.extensions.activityViewModelProvider
import com.themovielist.util.extensions.getScreenSize
import com.themovielist.widget.MovieDetailSectionView
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class MovieDetailActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var apiConfigurationFactory: ApiConfigurationFactory
    lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieImageGenreViewModel = intent.getParcelableExtra<MovieImageGenreViewModel>(EXTRA_MOVIE_MODEL)

        movieDetailViewModel = activityViewModelProvider(viewModelFactory)
        movieDetailViewModel.setMovieId(movieImageGenreViewModel.movieModel.id)

        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(this, R.layout.activity_movie_detail).also { it ->
            it.movieImageGenreViewModel = movieImageGenreViewModel
            it.viewModel = movieDetailViewModel
            it.screenWidth = getScreenSize().widthPixels.toFloat()
            it.backdropApiImageSizeList = apiConfigurationFactory.apiConfigurationModel.backdropImageSizes

            configureMovieReviewContent(it.mdsMovieDetailReviewSection as MovieDetailSectionView<MovieReviewModel>)

            movieDetailViewModel.reviewList.observe(this, Observer { reviewList ->
                Timber.d("Binding the review list")
                it.mdsMovieDetailReviewSection.bind(reviewList)
            })

            movieDetailViewModel.trailerList.observe(this, Observer { trailerList ->
                Timber.d("Binding the trailer list")
                it.mdsMovieDetailTrailerSection.bind(trailerList)
            })

            // configureAppBar(it.appBar, it.toolbar, movieImageGenreViewModel.movieModel.title)
        }
    }

    private fun configureMovieReviewContent(mdsMovieDetailReviewSection: MovieDetailSectionView<MovieReviewModel>) {
        mdsMovieDetailReviewSection.onBindSectionContent = { itemView, movieReviewModel ->
            // MovieDetailReviewViewHolder.bindLayout(itemView, movieReviewModel.author, movieReviewModel.content)
        }
        mdsMovieDetailReviewSection.onClickSectionButton = {
            // mPresenter.showAllReviews()
        }
    }

    private fun configureAppBar(appBar: AppBarLayout, toolbar: Toolbar, title: String) {
        var isShow = false
        var scrollRange = -1
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                toolbar.title = title
                isShow = true
            } else if(isShow) {
                toolbar.title = ""
                isShow = false
            }
        })
    }

    companion object {
        private const val EXTRA_MOVIE_MODEL = "extra_movie_model"

        fun getIntent(context: Context, movieImageGenreViewModel: MovieImageGenreViewModel): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_MODEL, movieImageGenreViewModel)
            }
        }
    }
}