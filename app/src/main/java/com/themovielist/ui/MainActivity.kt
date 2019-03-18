package com.themovielist.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.themovielist.R
import com.themovielist.di.ViewModelFactory
import com.themovielist.enumerations.TabType
import com.themovielist.extensions.injector
import com.themovielist.extensions.safeNullObserve
import com.themovielist.ui.FavoriteFragment.FavoriteFragment
import com.themovielist.ui.base.BaseViewModelActivity
import com.themovielist.ui.cinema.CinemaFragment
import com.themovielist.ui.home.HomeFragment
import com.themovielist.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseViewModelActivity<MainActivityViewModel>() {
    override val viewModelClass: Class<MainActivityViewModel>
        get() = MainActivityViewModel::class.java
    override val viewModelFactory: ViewModelFactory<MainActivityViewModel>
        get() = injector.mainActivityViewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.selectedTab.safeNullObserve(this) { tabType: TabType ->
            Timber.d("Changing the selected tab: $tabType")
            handleSelectedTab(tabType)
        }

        bottom_navigation_view.setOnNavigationItemSelectedListener { item: MenuItem ->
            val selectedTabType = getSelectedTabTypeByMenuItem(item)
            viewModel.setSelectedTab(selectedTabType)

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun getSelectedTabTypeByMenuItem(item: MenuItem) = when (item.itemId) {
        R.id.menu_item_home -> TabType.HOME
        R.id.menu_item_search -> TabType.SEARCH
        R.id.menu_item_cinema -> TabType.CINEMA
        else -> TabType.FAVORITE
    }

    private fun handleSelectedTab(tabType: TabType) {
        val fragment = when (tabType) {
            TabType.HOME -> HomeFragment.getInstance()
            TabType.SEARCH -> SearchFragment.getInstance()
            TabType.CINEMA -> CinemaFragment.getInstance()
            TabType.FAVORITE -> FavoriteFragment.getInstance()
        }

        replaceFragment(fragment)
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment {
        supportFragmentManager.beginTransaction()
                .replace(FRAGMENT_CONTAINER_ID, fragment)
                .commitNow()
    }

    companion object {
        private const val FRAGMENT_CONTAINER_ID = R.id.fragment_container
    }
}
