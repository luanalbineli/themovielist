package com.themovielist.ui.movieDetail.reviewList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovielist.R
import com.themovielist.model.response.MovieReviewResponseModel
import com.themovielist.widget.dialog.FullscreenListDialog
import kotlinx.android.synthetic.main.dialog_list_fullscreen.*

class MovieReviewListDialog : FullscreenListDialog<MovieReviewResponseModel>() {
    private val mMovieReviewAdapter by lazy { MovieReviewAdapter() }

    private val mLinearLayoutManager by lazy {
        LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(context, mLinearLayoutManager.orientation)

        list_fullscreen_dialog.addItemDecoration(dividerItemDecoration)
        list_fullscreen_dialog.layoutManager = mLinearLayoutManager
        list_fullscreen_dialog.adapter = mMovieReviewAdapter

        mMovieReviewAdapter.submitList(mList)

        setTitle(R.string.text_review_list)
    }

    companion object {
        fun getInstance(movieModelList: List<MovieReviewResponseModel>): MovieReviewListDialog {
            return createNewInstance(MovieReviewListDialog::class.java, movieModelList)
        }
    }
}
