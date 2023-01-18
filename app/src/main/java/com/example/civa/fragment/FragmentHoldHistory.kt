package com.example.civa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.civa.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import viewpagers.ViewPagerHistory

class FragmentHoldHistory:Fragment(R.layout.fragment_history_holder) {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerFragmentAdapter: ViewPagerHistory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.tableLayoutHistory1)
        viewPager2 = view.findViewById(R.id.viewPagerHistory1)
        viewPagerFragmentAdapter = ViewPagerHistory(requireActivity() as AppCompatActivity)

        viewPager2.adapter = viewPagerFragmentAdapter

        val listOfSubjects = listOf("სამატრიცე", "ვექტორები", "010")
        TabLayoutMediator(tabLayout, viewPager2){tab, position ->
            tab.text = listOfSubjects[position]
        }.attach()
    }
}
