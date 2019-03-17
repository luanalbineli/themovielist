package com.themovielist.widget.recyclerview

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.themovielist.widget.RequestStatusView
import kotlinx.android.synthetic.main.item_request_status.view.*


class RequestStatusViewHolder internal constructor(itemView: View) : CustomViewHolder(itemView) {
    fun bind(status: RequestStatusView.Status, isListEmpty: Boolean, errorMessageResId: Int?, emptyMessageResId: Int?, onTryAgain: (() -> Unit)? = null) {
        val layoutParams = itemView.view_request_status.layoutParams
        if (isListEmpty) {
            layoutParams.height = MATCH_PARENT
        } else {
            layoutParams.height = WRAP_CONTENT
        }
        itemView.view_request_status.onTryAgain = onTryAgain
        itemView.view_request_status.setErrorMessage(errorMessageResId)
        itemView.view_request_status.setEmptyMessage(emptyMessageResId)
        itemView.view_request_status.layoutParams = layoutParams
        itemView.view_request_status.toggleStatus(status)
    }
}
