package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentHome:Fragment(R.layout.fragment_home) {
    private lateinit var imageButtonMatrix:ImageButton
    private lateinit var imageButtonVector:ImageButton
    private lateinit var imageButtonFormulas:ImageButton
    private lateinit var imageButtonBinary:ImageButton
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageButtonMatrix = view.findViewById(R.id.imageButtonMatrix)

        imageButtonMatrix.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentSamatrice()
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)

            bottomNavigationView.visibility = View.GONE
            findNavController().navigate(action)
        }

        imageButtonVector = view.findViewById(R.id.imageButtonVector)

        imageButtonVector.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.fragmentHistory)
        }


    }
}