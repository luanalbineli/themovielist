package com.themovielist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovielist.enumerations.TabType
import com.themovielist.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(): BaseViewModel() {
    private val mSelectedTab = MutableLiveData(TabType.HOME)
    val selectedTab: LiveData<TabType>
        get() = mSelectedTab

    fun setSelectedTab(selectedTabType: TabType) {
        if (selectedTab.value == selectedTabType) {
            return
        }
        mSelectedTab.value = selectedTabType
    }
}