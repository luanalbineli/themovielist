package com.themovielist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.themovielist.R
import com.themovielist.extension.setDisplay
import kotlinx.android.synthetic.main.movie_detail_section.view.*
import java.security.InvalidParameterException

class MovieDetailSectionView<TModel> constructor(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    private val mLayoutInflater by lazy { LayoutInflater.from(context) }
    private val mTitleText: String

    private val mContentLayoutResId: Int

    private var mButtonTitle: String

    private var mEmptyMessage: String

    var onCreateSectionContent: ((parentView: ViewGroup, layoutInflater: LayoutInflater, TModel) -> Unit)? = null

    var onClickSectionButton: (() -> Unit)? = null

    init {
        mLayoutInflater.inflate(R.layout.movie_detail_section, this)
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MovieDetailSectionView, 0, 0)

        try {
            mContentLayoutResId = attributes.getResourceId(R.styleable.MovieDetailSectionView_content, -1)

            if (mContentLayoutResId == -1) {
                throw InvalidParameterException("The layout is required")
            }

            mTitleText = attributes.getString(R.styleable.MovieDetailSectionView_title) ?: ""
            mButtonTitle = attributes.getString(R.styleable.MovieDetailSectionView_buttonTitle) ?: ""
            mEmptyMessage = attributes.getString(R.styleable.MovieDetailSectionView_emptyMessage) ?: ""
        } finally {
            attributes.recycle()
        }

        movie_detail_section_title.text = mTitleText
        section_empty_message.text = mEmptyMessage
    }

    fun bind(sectionList: List<TModel>) {
        if (sectionList.isEmpty()) {
            section_see_all.setDisplay(false)
        } else {
            bindSectionContent(sectionList)
        }
    }

    private fun bindSectionContent(sectionList: List<TModel>) {
        section_empty_message.setDisplay(false)
        if (sectionList.size > 1) {
            section_see_all.text = String.format(mButtonTitle, sectionList.size)
            section_see_all.setOnClickListener { onClickSectionButton?.invoke() }
        } else {
            section_see_all.setDisplay(false)
        }
        onCreateSectionContent?.invoke(section_movie_detail_container, mLayoutInflater, sectionList.first())
    }
}