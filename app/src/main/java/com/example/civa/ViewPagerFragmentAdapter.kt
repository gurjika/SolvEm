package com.example.civa

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.civa.fragment.FragmentHelp
import com.example.civa.fragment.FragmentHelp2
import com.example.civa.fragment.FragmentHelpBinary

class ViewPagerFragmentAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentHelp()
            }
            1 -> {
                FragmentHelp2()
            }
            else -> {
                FragmentHelpBinary()
            }
        }
    }

}