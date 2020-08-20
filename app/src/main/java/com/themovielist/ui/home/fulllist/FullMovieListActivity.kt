package com.themovielist.ui.home.fulllist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.themovielist.R
import com.themovielist.databinding.ActivityFullMovieListBinding
import com.themovielist.enums.HomeMovieSortType
import com.themovielist.extension.safeNullObserve
import com.themovielist.ui.movieList.MovieListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.include_appbar.view.*

@AndroidEntryPoint
class FullMovieListActivity : AppCompatActivity() {
    private val viewModel: FullMovieListViewModel by viewModels()

    private val mMovieListFragment: MovieListFragment? by lazy {
        supportFragmentManager.findFragmentByTag(
            getString(R.string.tag_movie_list)
        ) as? MovieListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sortType = intent.getSerializableExtra(MOVIE_SORT_BUNDLE_KEY) as HomeMovieSortType
        viewModel.init(sortType)

        DataBindingUtil.setContentView<ActivityFullMovieListBinding>(
            this,
            R.layout.activity_full_movie_list
        ).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = this

            configureToolbar(binding.appBar.toolbar, sortType)

            configureMovieListFragment()
        }

        viewModel.movieList.safeNullObserve(this) {
            mMovieListFragment?.setMovieListResult(it)
        }
    }

    private fun configureMovieListFragment() {
        mMovieListFragment?.onLoadMoreItems = {
            viewModel.loadMoreMovies()
        }

        mMovieListFragment?.onTryAgain = {
            viewModel.tryFetchHomeDataAgain()
        }
    }

    private fun configureToolbar(
        toolbar: Toolbar,
        sortType: HomeMovieSortType
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(
            if (sortType == HomeMovieSortType.POPULARITY)
                R.string.text_home_most_popular
            else
                R.string.text_home_most_rated
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val MOVIE_SORT_BUNDLE_KEY = "movie_sort"

        fun getIntent(context: Context, homeMovieSortType: HomeMovieSortType): Intent {
            return Intent(context, FullMovieListActivity::class.java).also {
                it.putExtra(MOVIE_SORT_BUNDLE_KEY, homeMovieSortType)
            }
        }
    }
}