package com.themovielist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.themovielist.R
import com.themovielist.extensions.setDisplay
import kotlinx.android.synthetic.main.request_status.view.*


class RequestStatusView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    var onTryAgain: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.request_status, this)
        button_request_status_try_again.setOnClickListener {
            onTryAgain?.invoke()
        }
    }

    fun toggleStatus(status: Status) {
        view_request_status_loading.setDisplay(status == Status.LOADING)
        view_request_status_error.setDisplay(status == Status.ERROR)
        view_request_status_empty.setDisplay(status == Status.EMPTY)
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

    enum class Status {
        LOADING,
        ERROR,
        EMPTY
    }
}