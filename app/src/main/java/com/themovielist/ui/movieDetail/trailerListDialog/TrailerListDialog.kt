package com.themovielist.ui.movieDetail.trailerListDialog

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.model.response.MovieTrailerResponseModel
import com.themovielist.widget.dialog.FullscreenListDialog
import kotlinx.android.synthetic.main.dialog_list_fullscreen.*

class MovieTrailerListDialog : FullscreenListDialog<MovieTrailerResponseModel>() {
    private val mMovieReviewAdapter by lazy { MovieTrailerAdapter() }

    private val mLinearLayoutManager by lazy { LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(context, mLinearLayoutManager.orientation)

        list_fullscreen_dialog.addItemDecoration(dividerItemDecoration)
        list_fullscreen_dialog.layoutManager = mLinearLayoutManager
        list_fullscreen_dialog.adapter = mMovieReviewAdapter

        mMovieReviewAdapter.submitList(mList)

        setTitle(R.string.text_trailer_list)
    }

    companion object {
        fun getInstance(movieModelList: List<MovieTrailerResponseModel>): MovieTrailerListDialog {
            return createNewInstance(MovieTrailerListDialog::class.java, movieModelList)
        }
    }
}
