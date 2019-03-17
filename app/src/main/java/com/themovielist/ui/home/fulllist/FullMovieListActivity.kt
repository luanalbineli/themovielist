package com.themovielist.ui.home.fulllist

import android.content.Context
import android.content.Intent
import com.themovielist.di.ViewModelFactory
import com.themovielist.extensions.injector
import com.themovielist.ui.base.BaseViewModelActivity

class FullMovieListActivity: BaseViewModelActivity<FullHomeListViewModel>() {
    override val viewModelClass: Class<FullHomeListViewModel>
        get() = FullHomeListViewModel::class.java
    override val viewModelFactory: ViewModelFactory<FullHomeListViewModel>
        get() = injector.fullHomeListViewModel()

    companion object {
        const val SORT_POPULAR = 1
        const val SORT_RATING = 2
        private const val SORT_BUNDLE = "sort_movies"

        fun getIntent(context: Context, sort: Int): Intent {
            return Intent(context, FullMovieListActivity::class.java).also {
                it.putExtra(SORT_BUNDLE, sort)
            }
        }
    }
}