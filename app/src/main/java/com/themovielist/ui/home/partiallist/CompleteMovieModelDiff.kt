package com.themovielist.ui.home.partiallist

import androidx.recyclerview.widget.DiffUtil
import com.themovielist.model.view.CompleteMovieModel

object CompleteMovieModelDiff {
    @JvmField
    val Diff = object: DiffUtil.ItemCallback<CompleteMovieModel>() {
        override fun areItemsTheSame(oldItem: CompleteMovieModel, newItem: CompleteMovieModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CompleteMovieModel, newItem: CompleteMovieModel): Boolean {
            return oldItem.movieModel == newItem.movieModel
                    && oldItem.watched == newItem.watched
                    && oldItem.favorite == newItem.watched
        }

    }
}