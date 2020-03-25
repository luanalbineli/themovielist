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

    fun toggleStatus(status: Status) {
        loading_request_status.setDisplay(status == Status.LOADING)
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