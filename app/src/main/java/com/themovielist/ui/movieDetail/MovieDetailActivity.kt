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
import com.themovielist.databinding.*
import com.themovielist.extension.*
import com.themovielist.model.response.MovieReviewResponseModel
import com.themovielist.model.response.MovieTrailerResponseModel
import com.themovielist.model.response.Status
import com.themovielist.model.view.MovieModel
import com.themovielist.ui.movieDetail.trailerListDialog.MovieTrailerListDialog
import com.themovielist.widget.MovieDetailSectionView
import kotlinx.android.synthetic.main.activity_movie_detail.*

@Suppress("UNCHECKED_CAST")
class MovieDetailActivity : AppCompatActivity() {
    private val viewModel: MovieDetailViewModel by viewModels(factoryProducer = { injector.movieDetailViewModelFactory() })

    private val mMovieDetailSectionReview by lazy { section_view_movie_detail_review as MovieDetailSectionView<MovieReviewResponseModel> }
    private val mMovieDetailSectionTrailer by lazy { section_view_movie_detail_trailer as MovieDetailSectionView<MovieTrailerResponseModel> }

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

            configureMovieReviewContent()
            configureMovieTrailerContent()
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

        viewModel.movie.safeNullObserve(this) {
            invalidateOptionsMenu()
        }

        viewModel.showFullMovieReviewList.safeNullObserve(this) {
            // TODO: Show movie review list dialog
        }

        viewModel.showFullMovieTrailerList.safeNullObserve(this) {
            val dialog = MovieTrailerListDialog.getInstance(it)
            dialog.show(supportFragmentManager, MOVIE_TRAILER_LIST_TAG)
        }
    }

    private fun configureMovieReviewContent() {
        mMovieDetailSectionReview.onCreateSectionContent =
            { parentView, layoutInflater, movieReviewModel ->
                ItemMovieReviewBinding.inflate(layoutInflater, parentView, true).also {
                    it.reviewModel = movieReviewModel
                    it.executePendingBindings()
                }
            }
    }

    private fun configureMovieTrailerContent() {
        mMovieDetailSectionTrailer.onCreateSectionContent =
            { parentView, layoutInflater, movieTrailerModel ->
                ItemMovieTrailerBinding.inflate(layoutInflater, parentView, true).also {
                    it.trailerModel = movieTrailerModel
                    it.executePendingBindings()
                }
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
        private const val MOVIE_TRAILER_LIST_TAG = "MOVIE_TRAILER_LIST_TAG"

        fun getIntent(context: Context, movieModel: MovieModel): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_MODEL, movieModel)
            }
        }
    }
}