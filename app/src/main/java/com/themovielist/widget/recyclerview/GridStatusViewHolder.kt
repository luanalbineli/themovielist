package com.themovielist.widget.recyclerview

import android.view.View
import androidx.annotation.StringRes
import com.themovielist.widget.RequestStatusView
import kotlinx.android.synthetic.main.grid_status.view.*
import timber.log.Timber


class GridStatusViewHolder internal constructor(itemView: View, tryAgainClick: (() -> Unit)?, @StringRes emptyMessageResId: Int) : CustomRecyclerViewHolder(itemView) {
    init {
        itemView.rsvRequestStatus.setEmptyMessage(emptyMessageResId)
        itemView.rsvRequestStatus.setTryAgainClickListener(tryAgainClick)
    }

    fun bind(requestStatus: RequestStatusView.RequestStatus, numberOfItems: Int) {
        itemView.rsvRequestStatus.setRequestStatus(requestStatus, numberOfItems == 0)
        Timber.i("REDRAWING GRID STATUS: $requestStatus")
    }
}
