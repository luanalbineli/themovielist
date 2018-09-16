package com.themovielist.ui.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.themovielist.R
import com.themovielist.databinding.ActivityMovieDetailBinding
import com.themovielist.model.view.MovieImageGenreViewModel
import dagger.android.support.DaggerAppCompatActivity

class MovieDetailActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieImageGenreViewModel = intent.getParcelableExtra<MovieImageGenreViewModel>(EXTRA_MOVIE_MODEL)

        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(this, R.layout.activity_movie_detail).also {
            it.model = movieImageGenreViewModel
        }
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