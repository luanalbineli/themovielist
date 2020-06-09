package com.themovielist.extension

fun Double.format(digits: Int): String = String.format("%.${digits}f", this)