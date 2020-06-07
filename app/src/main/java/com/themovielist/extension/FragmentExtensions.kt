package com.themovielist.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.themovielist.MainApplication

val Fragment.injector get() = (requireActivity().application as MainApplication).component

fun Fragment.showSnackBarMessage(@StringRes messageResId: Int) = Snackbar.make(
    requireActivity().findViewById(android.R.id.content),
    messageResId, Snackbar.LENGTH_LONG
).show()