package com.themovielist.util.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context.getScreenSize(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}