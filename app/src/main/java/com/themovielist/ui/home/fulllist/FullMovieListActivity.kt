package com.themovielist.ui.home.fulllist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FullMovieListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
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