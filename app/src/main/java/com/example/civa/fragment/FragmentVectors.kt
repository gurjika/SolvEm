package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class FragmentVectors: Fragment(R.layout.fragment_vectors) {

    private lateinit var buttonGoToDot:Button
    private lateinit var buttonGoToCross:Button
    private lateinit var buttonGoToM:Button
    private lateinit var buttonGoToCos:Button
    private lateinit var buttonGoToSin:Button
    private lateinit var buttonGoToBuilt:Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGoToDot = view.findViewById(R.id.buttonGoToDot)
        buttonGoToCross = view.findViewById(R.id.buttonGoToCross)
        buttonGoToM = view.findViewById(R.id.buttonGoToM)
        buttonGoToCos = view.findViewById(R.id.buttonGoToCos)
        buttonGoToSin = view.findViewById(R.id.buttonGoToSin)

        buttonGoToCos.setOnClickListener {
            val action = FragmentVectorsDirections
                .actionFragmentVectorsToFragmentFigureVector("COS")
            findNavController().navigate(action)
        }
        buttonGoToDot.setOnClickListener {
            val action = FragmentVectorsDirections
                .actionFragmentVectorsToFragmentFigureVector("DOT")
            findNavController().navigate(action)
        }
        buttonGoToSin.setOnClickListener {
            val action = FragmentVectorsDirections
                .actionFragmentVectorsToFragmentVectorHarder("SIN")
            findNavController().navigate(action)
        }
        buttonGoToM.setOnClickListener {
            val action = FragmentVectorsDirections
                .actionFragmentVectorsToFragmentVectorHarder("MIXED")
            findNavController().navigate(action)
        }
        buttonGoToCross.setOnClickListener {
            val action = FragmentVectorsDirections
                .actionFragmentVectorsToFragmentVectorHarder("CROSS")
            findNavController().navigate(action)
        }

    }
}