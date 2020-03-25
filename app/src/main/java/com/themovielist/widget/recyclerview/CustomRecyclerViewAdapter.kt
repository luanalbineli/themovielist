package com.themovielist.widget.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovielist.R
import com.themovielist.widget.RequestStatusView
import timber.log.Timber

abstract class CustomRecyclerViewAdapter<TModel, THolder: BaseViewHolder> constructor(diffCallback: DiffUtil.ItemCallback<TModel>): ListAdapter<TModel, BaseViewHolder>(diffCallback) {
    var onItemClick: ((adapterPosition: Int, model: TModel) -> Unit)? = null
    var onTryAgain: (() -> Unit)? = null

    @StringRes
    var errorMessageResId: Int? = null

    @StringRes
    var emptyMessageResId: Int? = null

    private var mItems = emptyList<TModel>()
    val items: List<TModel>
        get() = mItems

    private var mRequestStatus: RequestStatusView.Status? = null

    private var mRequestErrorModel: Exception? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == ViewType.GRID_STATUS.ordinal) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_request_status, parent, false)
            return RequestStatusViewHolder(itemView)
        }

        val layoutVH = onCreateItemViewHolder(LayoutInflater.from(parent.context), parent, viewType)
        layoutVH.itemView.setOnClickListener {
            if (layoutVH.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick?.invoke(layoutVH.adapterPosition, getItem(layoutVH.adapterPosition))
            }
        }

        return layoutVH
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == ViewType.GRID_STATUS.ordinal) {
            Timber.d("Redrawing the request status: $mRequestStatus - position: $position")
            val requestStatusVH = holder as RequestStatusViewHolder
            requestStatusVH.bind(mRequestStatus!!, items.isEmpty(), errorMessageResId, emptyMessageResId, onTryAgain)
            return
        }

        @Suppress("UNCHECKED_CAST")
        onBindItemViewHolder(holder as THolder, position)
    }

    final override fun getItemViewType(position: Int): Int {
        if (position == super.getItemCount()) {
            return ViewType.GRID_STATUS.ordinal
        }
        return ViewType.ITEM.ordinal
    }

    fun showLoadingStatus() {
        redrawGridStatus(RequestStatusView.Status.LOADING)
    }

    fun showErrorStatus(exception: Exception? = null) {
        mRequestErrorModel = exception
        redrawGridStatus(RequestStatusView.Status.ERROR)
    }

    fun showEmptyStatus() {
        redrawGridStatus(RequestStatusView.Status.EMPTY)
    }

    private fun redrawGridStatus(status: RequestStatusView.Status? = null) {
        if (status == mRequestStatus) { // Already in the state
            return
        }

        val previousRequestStatus = mRequestStatus
        mRequestStatus = status
        if (mRequestStatus == null) {
            notifyItemRemoved(itemSize)
        } else if (previousRequestStatus == null) {
            notifyItemInserted(itemSize)
        } else {
            @Suppress("DEPRECATION")
            Timber.d("Changing the request status. Size list: $itemSize. Total: $itemCount")
            notifyItemChanged(itemSize)
        }
    }

    override fun submitList(list: List<TModel>?) {
        redrawGridStatus()

        mItems = list ?: emptyList()

        super.submitList(list)
    }

    private val itemSize
        get() = super.getItemCount()

    @Deprecated("Use itemSize")
    override fun getItemCount(): Int {
        return super.getItemCount() + if (mRequestStatus == null) 0 else 1
    }

    protected abstract fun onCreateItemViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): THolder

    protected abstract fun onBindItemViewHolder(holder: THolder, position: Int)
}

private enum class ViewType {
    GRID_STATUS,
    ITEM
}