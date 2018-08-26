package com.themovielist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.themovielist.R
import kotlinx.android.synthetic.main.request_status.view.*


class RequestStatusView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var mRequestStatus = RequestStatus.HIDDEN

    private var mTryAgainClickListener: (() -> Unit)? = null

    init {
        initializeViews(context)
    }

    private fun initializeViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.request_status, this)
    }

    fun setRequestStatus(requestStatus: RequestStatus, matchParentHeight: Boolean = false) {
        this.mRequestStatus = requestStatus
        redrawStatus(matchParentHeight)
    }

    fun setEmptyMessage(@StringRes messageResId: Int) {
        tvRequestStatusEmptyMessage.setText(messageResId)
    }

    private fun redrawStatus(matchParentHeight: Boolean) {
        toggleStatus(mRequestStatus == RequestStatus.LOADING,
                mRequestStatus == RequestStatus.ERROR,
                mRequestStatus == RequestStatus.EMPTY,
                if (matchParentHeight) MATCH_PARENT else WRAP_CONTENT)
    }

    private fun toggleStatus(loadingVisible: Boolean, errorVisible: Boolean, emptyMessageVisible: Boolean, viewHeight: Int) {
        pbRequestStatusLoading.visibility = if (loadingVisible) View.VISIBLE else View.INVISIBLE
        llRequestStatusError.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
        tvRequestStatusEmptyMessage.visibility = if (emptyMessageVisible) View.VISIBLE else View.INVISIBLE

        btRequestStatusRetry.setOnClickListener {
            mTryAgainClickListener?.invoke()
            }

        val layoutParams = this.layoutParams
        layoutParams.height = viewHeight
        this.layoutParams = layoutParams
        }

    fun setTryAgainClickListener(tryAgainClick: (() -> Unit)?) {
        mTryAgainClickListener = tryAgainClick
    }

    enum class RequestStatus {
        LOADING,
        ERROR,
        EMPTY,
        HIDDEN
    }
}
