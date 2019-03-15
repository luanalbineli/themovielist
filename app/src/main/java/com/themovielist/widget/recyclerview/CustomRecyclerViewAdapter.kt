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
import java.security.InvalidParameterException
import java.util.*

abstract class CustomRecyclerViewAdapter<TModel, THolder: BaseViewHolder> constructor(diffCallback: DiffUtil.ItemCallback<TModel>): ListAdapter<TModel, BaseViewHolder>(diffCallback) {
    var onItemClick: ((adapterPosition: Int, model: TModel) -> Unit)? = null
    var onTryAgain: (() -> Unit)? = null
    var errorMessageResId: Int? = null
    var emptyMessageResId: Int? = null

    var items = emptyList<TModel>()

    private var mRequestStatus: RequestStatusView.Status? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == ViewType.GRID_STATUS.ordinal) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_request_status, parent, false)
            return RequestStatusVH(itemView)
        }
        return onCreateItemViewHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == ViewType.GRID_STATUS.ordinal) {
            Timber.d("Redrawing the request status: $mRequestStatus - position: $position")
            val requestStatusVH = holder as RequestStatusVH
            requestStatusVH.bind(mRequestStatus!!, super.getItemCount() == 0, errorMessageResId, emptyMessageResId, onTryAgain)
            return
        }

        @Suppress("UNCHECKED_CAST")
        onBindItemViewHolder(holder as THolder, position)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(holder.adapterPosition, getItem(position))
        }
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

    fun showErrorStatus() {
        redrawGridStatus(RequestStatusView.Status.ERROR)
    }

    fun showEmptyStatus() {
        redrawGridStatus(RequestStatusView.Status.EMPTY)
    }

    private fun redrawGridStatus(status: RequestStatusView.Status? = null) {
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

    lateinit var recyclerView: BaseRecyclerView

    override fun submitList(list: List<TModel>?) {
        redrawGridStatus()

        items = list ?: emptyList()

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
