package com.themovielist.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.sackcentury.shinebuttonlib.ShineButton
import com.themovielist.R
import com.themovielist.databinding.ActivityMovieDetailBinding
import com.themovielist.databinding.MovieReviewItemBinding
import com.themovielist.databinding.MovieTrailerItemBinding
import com.themovielist.extension.*
import com.themovielist.model.response.MovieReviewModel
import com.themovielist.model.response.MovieTrailerModel
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.widget.MovieDetailSectionView
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    private val viewModel: MovieDetailViewModel by viewModels(factoryProducer = { injector.movieDetailViewModelFactory() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieModel = intent.getParcelableExtra<MovieModel>(EXTRA_MOVIE_MODEL)!!
        viewModel.init(movieModel)

        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        ).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
            it.backdropApiImageSizeList =
                viewModel.apiConfigurationFactory.apiConfigurationModel.backdropImageSizes
            it.screenWidth = getScreenSize().widthPixels.toFloat()

            configureToolbar(it.toolbar, movieModel.movieResponseModel.title)

            configureMovieReviewContent(it.sectionViewMovieDetailReview as MovieDetailSectionView<MovieReviewModel>)
            configureMovieTrailerContent(it.sectionViewMovieDetailTrailer as MovieDetailSectionView<MovieTrailerModel>)
        }

        attachListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_detail, menu)
        menu?.let {
            viewModel.movie.value?.let { movieModel ->
                bindMenuItem(menu,
                    R.id.menu_item_favorite,
                    R.id.button_movie_item_menu_favorite,
                    movieModel.isFavorite,
                    View.OnClickListener { viewModel.toggleMovieFavorite() })

                bindMenuItem(menu,
                    R.id.menu_item_watched,
                    R.id.button_movie_item_menu_watched,
                    movieModel.isWatched,
                    View.OnClickListener { viewModel.toggleMovieWatched() })
            }
        }
        return true
    }

    private fun attachListeners() {
        viewModel.toggleMovieFavorite.safeNullObserve(this) { result ->
            if (result.status == Status.SUCCESS) {
                showToggleMovieFavoriteMessage(result.data!!)
            } else if (result.status == Status.ERROR) {
                showSnackBarMessage(R.string.error_favorite_movie_text)
                invalidateOptionsMenu()
            }
        }

        viewModel.toggleMovieWatched.safeNullObserve(this) { result ->
            if (result.status == Status.SUCCESS) {
                showToggleMovieWatchedMessage(result.data!!)
            } else if (result.status == Status.ERROR) {
                showSnackBarMessage(R.string.error_watched_movie_text)
                invalidateOptionsMenu()
            }
        }

        viewModel.movieDetail.safeNullObserve(this) {
            // TODO: Handle the status
            if (it.data != null) {
                (section_view_movie_detail_review as MovieDetailSectionView<MovieReviewModel>).bind(it.data.reviews.results)
                (section_view_movie_detail_trailer as MovieDetailSectionView<MovieTrailerModel>).bind(it.data.trailers.trailerList)
            }
        }

        viewModel.movie.safeNullObserve(this) {
            invalidateOptionsMenu()
        }

        viewModel.showFullMovieReviewList.safeNullObserve(this) {
            // TODO: Show movie review list dialog
        }

        viewModel.showFullMovieTrailerList.safeNullObserve(this) {
            // TODO: Show movie trailer list dialog
        }
    }

    private fun configureMovieReviewContent(sectionView: MovieDetailSectionView<MovieReviewModel>) {
        sectionView.onCreateSectionContent =
            { parentView, layoutInflater, movieReviewModel ->
                MovieReviewItemBinding.inflate(layoutInflater, parentView, true).also {
                    it.reviewModel = movieReviewModel
                }
            }

        sectionView.onClickSectionButton = {
            viewModel.showFullMovieReviewList()
        }
    }

    private fun configureMovieTrailerContent(sectionView: MovieDetailSectionView<MovieTrailerModel>) {
        sectionView.onCreateSectionContent =
            { parentView, layoutInflater, movieTrailerModel ->
                MovieTrailerItemBinding.inflate(layoutInflater, parentView, true).also {
                    it.trailerModel = movieTrailerModel
                }
            }

        sectionView.onClickSectionButton = {
            viewModel.showFullMovieTrailerList()
        }
    }

    private fun bindMenuItem(
        menu: Menu,
        @IdRes menuItemId: Int,
        @IdRes buttonId: Int,
        isChecked: Boolean,
        onClick: View.OnClickListener
    ) {
        val favoriteMenuItem = menu.findItem(menuItemId)

        val buttonView = favoriteMenuItem.actionView.findViewById<ShineButton>(buttonId)
        buttonView.isChecked = isChecked
        buttonView.setOnClickListener(onClick)
    }

    private fun configureToolbar(toolbar: Toolbar, title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        this.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val EXTRA_MOVIE_MODEL = "extra_movie_model"

        fun getIntent(context: Context, movieModel: MovieModel): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_MODEL, movieModel)
            }
        }
    }
}