package com.themovielist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.themovielist.R
import com.themovielist.extension.setDisplay
import kotlinx.android.synthetic.main.view_movie_detail_section.view.*

class MovieDetailSectionView<TModel> constructor(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    private val mLayoutInflater by lazy { LayoutInflater.from(context) }
    private val mTitleText: String

    private val mShimmerContentLayoutResId: Int

    private var mButtonTitle: String

    private var mEmptyMessage: String

    var onCreateSectionContent: ((parentView: ViewGroup, layoutInflater: LayoutInflater, TModel) -> Unit)? = null

    var onClickSectionButton: (() -> Unit)? = null

    init {
        mLayoutInflater.inflate(R.layout.view_movie_detail_section, this)
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MovieDetailSectionView, 0, 0)

        try {
            mShimmerContentLayoutResId = attributes.getResourceId(R.styleable.MovieDetailSectionView_shimmerContent, -1)

           /* if (mShimmerContentLayoutResId == -1) {
                throw InvalidParameterException("The shimmer layout is required")
            }*/

            mTitleText = attributes.getString(R.styleable.MovieDetailSectionView_title) ?: ""
            mButtonTitle = attributes.getString(R.styleable.MovieDetailSectionView_buttonTitle) ?: ""
            mEmptyMessage = attributes.getString(R.styleable.MovieDetailSectionView_emptyMessage) ?: ""
        } finally {
            attributes.recycle()
        }

        text_movie_detail_section_title.text = mTitleText
        section_empty_message.text = mEmptyMessage
    }

    fun bind(itemList: List<TModel>) {
        if (itemList.isEmpty()) {
            text_movie_detail_section_see_all.setDisplay(false)
        } else {
            section_empty_message.setDisplay(false)
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