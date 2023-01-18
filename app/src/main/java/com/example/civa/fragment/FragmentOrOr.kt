package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class FragmentOrOr:Fragment(R.layout.fragment_or_or) {
    private lateinit var buttonAB: Button
    private lateinit var buttonBA:Button
    private lateinit var enter:Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAB = view.findViewById(R.id.buttonAB)
        buttonBA = view.findViewById(R.id.buttonBA)
        enter = view.findViewById(R.id.buttonEnterCriteria)

        var criteria = ""
        buttonAB.setOnClickListener {
            buttonAB.isEnabled = false
            buttonBA.isEnabled = true
            criteria = "<"
        }
        buttonBA.setOnClickListener {
            buttonBA.isEnabled = false
            buttonAB.isEnabled = true
            criteria = ">"
        }
        enter.setOnClickListener {
            val action = FragmentOrOrDirections.actionFragmentOrOrToFigureDimensionsForTwo("FINDX", criteria)
            findNavController().navigate(action)
        }

    }
}