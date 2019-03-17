package com.themovielist.ui.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.themovielist.R
import com.themovielist.databinding.ActivityMovieDetailBinding
import com.themovielist.databinding.MovieReviewItemBinding
import com.themovielist.databinding.MovieTrailerItemBinding
import com.themovielist.di.ViewModelFactory
import com.themovielist.extensions.injector
import com.themovielist.model.response.MovieReviewModel
import com.themovielist.model.response.MovieTrailerModel
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.ui.base.BaseViewModelActivity
import com.themovielist.widget.MovieDetailSectionView
import timber.log.Timber

class MovieDetailActivity : BaseViewModelActivity<MovieDetailViewModel>() {
    override val viewModelClass: Class<MovieDetailViewModel>
        get() = MovieDetailViewModel::class.java
    override val viewModelFactory: ViewModelFactory<MovieDetailViewModel>
        get() = injector.movieDetailViewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieImageGenreViewModel = intent.getParcelableExtra<CompleteMovieModel>(EXTRA_MOVIE_MODEL)

        viewModel.setMovieId(movieImageGenreViewModel.movieModel.id)

        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(this, R.layout.activity_movie_detail).also { it ->
            it.lifecycleOwner = this@MovieDetailActivity

            it.movieImageGenreViewModel = movieImageGenreViewModel
            it.viewModel = viewModel
            // it.screenWidth = getScreenSize().widthPixels.toFloat()
            // it.backdropApiImageSizeList = apiConfigurationFactory.apiConfigurationModel.backdropImageSizes

            configureMovieReviewContent(it.mdsMovieDetailReviewSection as MovieDetailSectionView<MovieReviewModel>)
            configureMovieTrailerContent(it.mdsMovieDetailTrailerSection as MovieDetailSectionView<MovieTrailerModel>)

            viewModel.reviewList.observe(this, Observer { reviewList ->
                Timber.d("Binding the review list")
                it.mdsMovieDetailReviewSection.bind(reviewList)
            })

            viewModel.trailerList.observe(this, Observer { trailerList ->
                Timber.d("Binding the trailer list")
                it.mdsMovieDetailTrailerSection.bind(trailerList)
            })

            setSupportActionBar(it.toolbar)
            supportActionBar?.apply {
                title = movieImageGenreViewModel.movieModel.title
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun configureMovieReviewContent(mdsMovieDetailReviewSection: MovieDetailSectionView<MovieReviewModel>) {
        mdsMovieDetailReviewSection.onCreateSectionContent = { parentView, layoutInflater, movieReviewModel ->
            MovieReviewItemBinding.inflate(layoutInflater, parentView, true).also {
                it.reviewModel = movieReviewModel
            }
        }

        mdsMovieDetailReviewSection.onClickSectionButton = {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_detail, menu)
        return true
    }

    private fun configureMovieTrailerContent(mdsMovieDetailReviewSection: MovieDetailSectionView<MovieTrailerModel>) {
        mdsMovieDetailReviewSection.onCreateSectionContent = { parentView, layoutInflater, movieTrailerModel ->
            MovieTrailerItemBinding.inflate(layoutInflater, parentView, true).also {
                it.trailerModel = movieTrailerModel
            }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val EXTRA_MOVIE_MODEL = "extra_movie_model"

        fun getIntent(context: Context, movieImageGenreViewModel: CompleteMovieModel): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_MODEL, movieImageGenreViewModel)
            }
        }
    }
}