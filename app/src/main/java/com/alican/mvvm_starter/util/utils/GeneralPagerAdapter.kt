package com.alican.mvvm_starter.util.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alican.mvvm_starter.base.BasePagerAdapter

/**
 * General View Pager Adapter
 */
class GeneralPagerAdapter(
    manager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
    private val titleList: Array<String>? = null
) : BasePagerAdapter(manager, lifecycleOwner.lifecycle) {

    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    override fun getFragments(): ArrayList<Fragment> {
        return mFragmentList
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitles(): Array<String>? {
        return titleList
    }
}