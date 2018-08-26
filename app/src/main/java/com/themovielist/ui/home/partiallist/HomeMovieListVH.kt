package com.themovielist.ui.home.partiallist

import android.view.View
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.themovielist.GlideApp
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.util.ApiUtil
import com.themovielist.widget.recyclerview.CustomRecyclerViewHolder
import kotlinx.android.synthetic.main.home_movie_list_item.view.*
import com.bumptech.glide.load.resource.bitmap.CenterCrop



class HomeMovieListVH(itemView: View)
    : CustomRecyclerViewHolder(itemView) {

    fun bind(movieImageViewModel: MovieImageGenreViewModel, posterWidthRequest: String) {
        movieImageViewModel.movieModel.posterPath?.let {
            val posterUrl = ApiUtil.buildPosterImageUrl(it, posterWidthRequest)
            GlideApp.with(context)
                    .load(posterUrl)
                    .transforms(CenterCrop(), RoundedCorners(12))
                    .into(itemView.movieImageView)
        }
        itemView.movieTitleTextView.text = movieImageViewModel.movieModel.title
    }
}
