package com.allens.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.allens.bean.home_system_tab.HomeSystemTabBean

class HomeFgPagerAdapter(
    private val list: List<Fragment>,
    private val bean: HomeSystemTabBean,
    fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return bean.data[position].name
    }
}