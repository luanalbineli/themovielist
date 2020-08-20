package com.themovielist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.themovielist.R
import com.themovielist.databinding.FragmentHomeBinding
import com.themovielist.extension.handleMovieActions
import com.themovielist.extension.safeNullObserve
import com.themovielist.extension.setDisplay
import com.themovielist.model.response.Status
import com.themovielist.ui.home.fulllist.FullMovieListActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_appbar.*

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(layoutInflater, container, false).let { binding ->
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        text_app_bar_title.setText(R.string.text_home)

        viewModel.homeMovieList.safeNullObserve(viewLifecycleOwner) {
            container_home.setDisplay(it.status != Status.ERROR)
            request_status_home.setDisplay(it.status == Status.ERROR)
        }

        viewModel.showFullMovieList.safeNullObserve(viewLifecycleOwner) { homeMovieSortType ->
            val intent = FullMovieListActivity.getIntent(requireContext(), homeMovieSortType)
            startActivity(intent)
        }

        handleMovieActions(viewModel)
    }
}