package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class FragmentSamatrice:Fragment(R.layout.fragment_samatrice) {
    private lateinit var toInverse:Button
    private lateinit var toMultiply:Button
    private lateinit var toAdd:Button
    private lateinit var toSubtract:Button
    private lateinit var toTransp:Button
    private lateinit var toFindX:Button
    private lateinit var toDeterminant:Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        toInverse = view.findViewById(R.id.buttonGoToInverse)
        toMultiply = view.findViewById(R.id.buttonGoToMultiply)
        toAdd = view.findViewById(R.id.buttonGoToAdd)
        toSubtract = view.findViewById(R.id.buttonGoToSubtract)
        toTransp = view.findViewById(R.id.buttonGoToT)
        toFindX = view.findViewById(R.id.buttonGoToFindX)
        toDeterminant = view.findViewById(R.id.buttonGoToDeterminant)

        toInverse.setOnClickListener {
            val action = FragmentSamatriceDirections.actionFragmentSamatriceToFragmenCalculate("INVERSE")
            findNavController().navigate(action)
        }
        toSubtract.setOnClickListener {
            val action = FragmentSamatriceDirections.actionFragmentSamatriceToFragmenCalculate("SUBTRACT")
            findNavController().navigate(action)
        }
        toAdd.setOnClickListener {
            val action = FragmentSamatriceDirections.actionFragmentSamatriceToFragmenCalculate("ADD")
            findNavController().navigate(action)
        }
        toMultiply.setOnClickListener {
            val action = FragmentSamatriceDirections
                .actionFragmentSamatriceToFigureDimensionsForTwo("MULTIPLY", "")
            findNavController().navigate(action)
        }
        toDeterminant.setOnClickListener {
             val action = FragmentSamatriceDirections
                 .actionFragmentSamatriceToFragmenCalculate("DETERMINANT")
            findNavController().navigate(action)
        }
        toTransp.setOnClickListener {
            val action = FragmentSamatriceDirections
                .actionFragmentSamatriceToFragmenCalculate("TRANSP")
            findNavController().navigate(action)
        }
        toFindX.setOnClickListener {
            val action = FragmentSamatriceDirections
                .actionFragmentSamatriceToFragmentOrOr()
            findNavController().navigate(action)
        }
    }
}