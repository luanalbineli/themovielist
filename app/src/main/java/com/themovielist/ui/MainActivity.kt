package com.themovielist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.themovielist.R
import com.themovielist.di.ViewModelFactory
import com.themovielist.extension.injector
import com.themovielist.ui.base.ViewModelActivity
import com.themovielist.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : ViewModelActivity<MainViewModel>() {
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
    override val viewModelFactory: ViewModelFactory<MainViewModel>
        get() = injector.mainViewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener {
            Timber.d("Setting up the main content")
            when (it.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                /*R.id.navigation_browse -> {
                    message.setText(R.string.title_dashboard)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cinema -> {
                    message.setText(R.string.title_notifications)
                    return@setOnNavigationItemSelectedListener true
                }*/
            }
            false
        }

        // Add a listener to prevent reselects from being treated as selects.
        navigation.setOnNavigationItemReselectedListener {}

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
        }
    }
}
