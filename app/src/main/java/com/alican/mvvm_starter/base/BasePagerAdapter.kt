package com.alican.mvvm_starter.base

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Base View Pager Adapter
 */
abstract class BasePagerAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {

    /**
     * Create fragment
     */
    override fun createFragment(position: Int): Fragment {
        return getFragments()[position]
    }

    /**
     * Returns item count
     */
    override fun getItemCount(): Int {
        return getFragments().size
    }

    /**
     * Returns an array which is full of fragments
     */
    protected abstract fun getFragments(): List<Fragment>

    protected abstract fun getPageTitles(): Array<String>?

}