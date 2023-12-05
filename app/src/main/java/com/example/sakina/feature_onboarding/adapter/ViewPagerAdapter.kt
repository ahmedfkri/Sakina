package com.example.sakina.feature_onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val list: ArrayList<Fragment>,
    fragmentManger: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManger, lifecycle) {

    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position]
}