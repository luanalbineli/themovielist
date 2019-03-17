package com.themovielist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.themovielist.R
import com.themovielist.di.ViewModelFactory
import com.themovielist.extensions.injector
import com.themovielist.ui.base.BaseViewModelActivity

class MainActivity : BaseViewModelActivity<MainActivityViewModel>() {
    override val viewModelClass: Class<MainActivityViewModel>
        get() = MainActivityViewModel::class.java
    override val viewModelFactory: ViewModelFactory<MainActivityViewModel>
        get() = injector.mainActivityViewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*navigation.setOnNavigationItemSelectedListener{
            Timber.d("Setting up the main content")
            when (it.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                *//*R.id.navigation_browse -> {
                    message.setText(R.string.title_dashboard)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cinema -> {
                    message.setText(R.string.title_notifications)
                    return@setOnNavigationItemSelectedListener true
                }*//*
            }
            false
        }

        // Add a listener to prevent reselects from being treated as selects.
        navigation.setOnNavigationItemReselectedListener {}

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }*/
    }

    private fun <F> replaceFragment(fragment: F) where F: Fragment {
        supportFragmentManager.beginTransaction()
                .replace(FRAGMENT_CONTAINER_ID, fragment)
                .commit()
    }

    companion object {
        private const val FRAGMENT_CONTAINER_ID = R.id.fragment_container
    }
}
