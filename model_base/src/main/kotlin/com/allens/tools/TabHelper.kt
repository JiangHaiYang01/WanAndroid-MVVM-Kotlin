package com.allens.model_base.tools

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 *
 * @Description:     选项卡
 * @Author:         Allens
 * @CreateDate:     2019-11-13 17:01
 * @Version:        1.0
 */
class TabHelper(
    private val manger: FragmentManager,
    private val list: List<Fragment>,
    private val radioGroup: RadioGroup,
    private val containerId: Int
) {

    private val showIndex = 0
    private var hideIndex = 0


    fun showTabToFragment(
    ) {
        (radioGroup.getChildAt(showIndex) as RadioButton).isChecked = true//初始化选中第一个
        showFragment(showIndex, hideIndex)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton =
                group.findViewById(checkedId)
            radioButton.isChecked = true
            val i = group.indexOfChild(radioButton)
            showFragment(i, hideIndex)
            hideIndex = i
        }
    }


    private fun showFragment(showIndex: Int, hideIndex: Int) {
        val showFragment: Fragment = list[showIndex]
        val hideFragment: Fragment = list[hideIndex]
        val ft: FragmentTransaction = manger.beginTransaction()
        if (!showFragment.isAdded) {
            ft.add(containerId, showFragment)
        }
        if (showIndex == hideIndex) {
            ft.show(showFragment)
        } else {
            ft.show(showFragment)
            ft.hide(hideFragment)
        }
        ft.commit()
    }
}


