package com.themovielist.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class PaginatedRecyclerView constructor(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {
    private var mLoading = false

    var onLoadMoreItems: (() -> Unit)? = null

    fun enableLoadMoreItems(layoutManager: LinearLayoutManager) {
        mLoading = false

        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                Timber.d("Total item count: $totalItemCount | Last: $lastVisibleItem")
                if (!mLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    Timber.d("Loading more items")
                    mLoading = true
                    onLoadMoreItems?.invoke()
                }
            }
        })
    }

    fun disableLoadMoreItems() {
        clearOnScrollListeners()
    }

    companion object {
        private const val visibleThreshold = 3
    }
}