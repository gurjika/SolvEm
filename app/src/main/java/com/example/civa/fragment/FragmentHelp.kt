package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.civa.R
import com.example.civa.ViewPagerFragmentAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class FragmentHelp: Fragment(R.layout.fragment_help) {

    private lateinit var button: Button
    private lateinit var button1temp:Button
    lateinit var sharedPreferencesTest: SharedPreferences
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button3)
        button1temp = view.findViewById(R.id.button4)
        sharedPreferencesTest = this.requireActivity().getSharedPreferences("Test",
            Context.MODE_PRIVATE
        )

        button1temp.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.fragmentAddOrSubtract)

        }
        button.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.fragmentInverse)
        }
    }
}