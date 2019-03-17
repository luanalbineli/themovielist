package com.themovielist.ui.moviedetail.review

import android.view.View
import com.themovielist.widget.recyclerview.CustomViewHolder
import kotlinx.android.synthetic.main.movie_review_item.view.*

class MovieDetailReviewViewHolder internal constructor(itemView: View)
    : CustomViewHolder(itemView) {

    fun bind(author: String, content: String) = bindLayout(itemView, author, content)

    companion object {
        fun bindLayout(itemView: View, author: String, content: String) {
            itemView.tvMovieReviewAuthor.text = author
            itemView.tvMovieReviewContent.text = content
        }
    }
}
