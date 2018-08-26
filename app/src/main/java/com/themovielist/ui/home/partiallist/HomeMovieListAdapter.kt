package com.themovielist.ui.home.partiallist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.themovielist.R
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.model.view.MovieImageModel
import com.themovielist.util.ApiUtil
import com.themovielist.util.extensions.dpToPx
import com.themovielist.widget.recyclerview.CustomRecyclerViewAdapter

internal class HomeMovieListAdapter(emptyMessageResId: Int, tryAgainClickListener: (() -> Unit)? = null) : CustomRecyclerViewAdapter<MovieImageGenreViewModel, HomeMovieListVH>(emptyMessageResId, tryAgainClickListener) {
    private var mPosterWidth: String? = null

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): HomeMovieListVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_movie_list_item, parent, false)
        return HomeMovieListVH(itemView)
    }

    override fun onBindItemViewHolder(holder: HomeMovieListVH, position: Int) {
        if (mPosterWidth == null) {
            val defaultImageWidth = holder.context.resources.getDimension(R.dimen.home_movie_list_image_width)
            val posterWidthPx = holder.context.dpToPx(defaultImageWidth)

            mPosterWidth = ApiUtil.getDefaultPosterSize(posterWidthPx.toInt())
        }

        val movieImageViewModel = getItemByPosition(position)
        holder.bind(movieImageViewModel, mPosterWidth!!)
    }
}