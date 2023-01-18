package com.example.civa.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class FragmentFigureVector:Fragment(R.layout.fragment_figure_vector) {
    private lateinit var buttonFigureTwo:Button
    private lateinit var buttonFigureThree:Button
    private lateinit var operationTextView:TextView
    private  var dimension = ""
    private var destination = ""
    private lateinit var buttonVectorEnter:Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonFigureTwo = view.findViewById(R.id.buttonFigureTwo)
        buttonFigureThree = view.findViewById(R.id.buttonFigureThree)
        operationTextView = view.findViewById(R.id.operationTextView)
        destination = FragmentFigureVectorArgs.fromBundle(requireArguments()).destination
        buttonVectorEnter = view.findViewById(R.id.buttonVectorDimensionEnter)

        buttonFigureThree.setOnClickListener {
            buttonFigureThree.background.setTint(Color.parseColor("#FF9800"))
            dimension = "3"
        }
        buttonFigureTwo.setOnClickListener {
            buttonFigureThree.background.setTint(Color.GRAY)
            dimension = "2"
        }
        buttonVectorEnter.setOnClickListener {
            if(destination == "COS") {
                val action = FragmentFigureVectorDirections
                    .actionFragmentFigureVectorToFragmentVectorEasier(dimension, destination,null , false)
                findNavController().navigate(action)
            }
            if(destination == "DOT"){
                val action = FragmentFigureVectorDirections
                    .actionFragmentFigureVectorToFragmentVectorEasier(dimension, destination,null , false)
                findNavController().navigate(action)
            }
        }
    }
}