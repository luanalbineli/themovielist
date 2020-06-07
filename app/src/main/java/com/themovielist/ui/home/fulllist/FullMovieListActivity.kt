package com.themovielist.ui.home.fulllist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.themovielist.R
import com.themovielist.enums.HomeMovieSortType
import com.themovielist.extension.injector
import com.themovielist.ui.home.HomeViewModel

class FullMovieListActivity: AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels(factoryProducer = { injector.homeViewModelFactory() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_movie_list)

        val sortType = intent.getSerializableExtra(MOVIE_SORT_BUNDLE_KEY) as HomeMovieSortType

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