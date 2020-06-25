package com.themovielist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.themovielist.R
import com.themovielist.extension.setDisplay
import kotlinx.android.synthetic.main.request_status_view.view.*
import timber.log.Timber

class RequestStatusView(context: Context, attributeSet: AttributeSet) :
        FrameLayout(context, attributeSet) {
    var onTryAgain: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.request_status_view, this)

        button_request_status_try_again.setOnClickListener {
            Timber.d("Try again: $onTryAgain")
            onTryAgain?.invoke()
        }
    }

    fun toggleStatus(requestStatus: RequestStatus) {
        loading_request_status.setDisplay(requestStatus == RequestStatus.LOADING)
        view_request_status_error.setDisplay(requestStatus == RequestStatus.ERROR)
        view_request_status_empty.setDisplay(requestStatus == RequestStatus.EMPTY)
    }

    fun setErrorMessage(stringResId: Int?) {
        if (stringResId == null) {
            text_request_status_error.text = ""
        } else {
            text_request_status_error.setText(stringResId)
        }
    }

    fun setEmptyMessage(emptyMessageResId: Int?) {
        if (emptyMessageResId == null) {
            text_request_status_empty.text = ""
        } else {
            text_request_status_empty.setText(emptyMessageResId)
        }
    }

    enum class RequestStatus {
        LOADING,
        ERROR,
        EMPTY
    }
}