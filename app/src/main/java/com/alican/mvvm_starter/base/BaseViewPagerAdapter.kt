/*
 * Created by Furkan Coşkun on 17.05.2022 16:07
 * Copyright © 2022 Furkan Coşkun. All rights reserved.
 */
package com.alican.mvvm_starter.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BaseViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    abstract var fragments: ArrayList<Fragment>

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}