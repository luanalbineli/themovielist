package com.themovielist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.getResourceIdOrThrow
import com.facebook.shimmer.ShimmerFrameLayout
import com.themovielist.R
import com.themovielist.extension.setDisplay
import kotlinx.android.synthetic.main.view_movie_detail_section.view.*
import timber.log.Timber

class MovieDetailSectionView<TModel> constructor(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    private val mLayoutInflater by lazy { LayoutInflater.from(context) }
    private val mTitleText: String

    private val mShimmerContentLayoutResId: Int

    private var mButtonTitle: String

    private var mEmptyMessage: String

    var onCreateSectionContent: ((parentView: ViewGroup, layoutInflater: LayoutInflater, TModel) -> Unit)? = null

    var onClickSectionButton: (() -> Unit)? = null

    private val mShimmerLayout: ShimmerFrameLayout by lazy { findViewById<ShimmerFrameLayout>(R.id.shimmer_movie_detail_section) }

    init {
        mLayoutInflater.inflate(R.layout.view_movie_detail_section, this)
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MovieDetailSectionView, 0, 0)

        try {
            mShimmerContentLayoutResId = attributes.getResourceIdOrThrow(R.styleable.MovieDetailSectionView_shimmerContent)

            mTitleText = attributes.getString(R.styleable.MovieDetailSectionView_title) ?: ""
            mButtonTitle = attributes.getString(R.styleable.MovieDetailSectionView_buttonTitle) ?: ""
            mEmptyMessage = attributes.getString(R.styleable.MovieDetailSectionView_emptyMessage) ?: ""
        } finally {
            attributes.recycle()
        }

        text_movie_detail_section_title.text = mTitleText
        section_empty_message.text = mEmptyMessage

        mLayoutInflater.inflate(mShimmerContentLayoutResId, view_movie_detail_section_container, true)
        mShimmerLayout.startShimmer()
    }

    fun setList(itemList: List<TModel>) {
        Timber.d("setList: $itemList")
    }

    fun bind(itemList: List<TModel>) {
        mShimmerLayout.stopShimmer()
        view_movie_detail_section_container.removeView(mShimmerLayout)

        if (itemList.isEmpty()) {
            text_movie_detail_section_see_all.setDisplay(false)
            section_empty_message.setDisplay(true)
        } else {
            if (itemList.size > 1) {
                text_movie_detail_section_see_all.text = String.format(mButtonTitle, itemList.size)
                text_movie_detail_section_see_all.setOnClickListener { onClickSectionButton?.invoke() }
            } else {
                text_movie_detail_section_see_all.setDisplay(false)
            }

            onCreateSectionContent?.invoke(view_movie_detail_section_container, mLayoutInflater, itemList.first())
        }
    }
}