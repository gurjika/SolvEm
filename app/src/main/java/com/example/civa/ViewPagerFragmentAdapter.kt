package com.example.civa

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.civa.fragment.FragmentHelp
import com.example.civa.fragment.FragmentHelp2

class ViewPagerFragmentAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return FragmentHelp()
        } else {
            return FragmentHelp2()
        }
    }

}