package com.themovielist.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovielist.MainApplication
import com.themovielist.di.ViewModelFactory

val AppCompatActivity.injector get() = (application as MainApplication).component