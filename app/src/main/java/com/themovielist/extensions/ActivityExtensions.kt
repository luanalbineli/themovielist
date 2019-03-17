package com.themovielist.extensions

import androidx.appcompat.app.AppCompatActivity
import com.themovielist.di.DaggerComponentProvider

val AppCompatActivity.injector get() = (application as DaggerComponentProvider).component