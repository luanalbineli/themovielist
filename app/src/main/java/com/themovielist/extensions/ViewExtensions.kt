package com.themovielist.extensions

import android.view.View

fun View.setDisplay(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.setVisibility(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}