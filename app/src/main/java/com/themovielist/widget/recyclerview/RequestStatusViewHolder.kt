package com.themovielist.widget.recyclerview

import android.view.View
import android.view.ViewGroup
import com.themovielist.widget.RequestStatusView
import kotlinx.android.synthetic.main.item_request_status.view.*

class RequestStatusViewHolder constructor(itemView: View): BaseViewHolder(itemView) {
    fun bind(requestStatus: RequestStatusView.RequestStatus, isListEmpty: Boolean, errorMessageResId: Int?, emptyMessageResId: Int?, onTryAgain: (() -> Unit)? = null) {
        val layoutParams = itemView.view_request_status.layoutParams
        if (isListEmpty) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        itemView.view_request_status.onTryAgain = onTryAgain
        itemView.view_request_status.setErrorMessage(errorMessageResId)
        itemView.view_request_status.setEmptyMessage(emptyMessageResId)
        itemView.view_request_status.layoutParams = layoutParams
        itemView.view_request_status.toggleStatus(requestStatus)
    }
}