package com.themovielist.ui.cinema

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.themovielist.R
import com.themovielist.databinding.FragmentHomeBinding
import com.themovielist.di.ApiConfigurationFactory
import com.themovielist.di.AppComponent
import com.themovielist.extensions.activityViewModelProvider
import com.themovielist.model.response.Result
import com.themovielist.model.response.Status
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.ui.base.BaseViewModelFragment
import com.themovielist.ui.common.Event
import com.themovielist.ui.home.partiallist.HomePartialListFragment
import com.themovielist.ui.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.include_appbar.view.*
import timber.log.Timber
import javax.inject.Inject

class CinemaFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return TextView(requireActivity()).also {
           it.id = R.id.text
       }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.text).also {
            val layoutParams = it.layoutParams
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
            it.text = "CinemaFragment"
            it.gravity = Gravity.CENTER
        }
    }

    companion object {
        fun getInstance() = CinemaFragment()
    }
}