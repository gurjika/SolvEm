package com.example.civa.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class FigureDimensionsForTwo:Fragment(R.layout.fragment_figure_dimensions_for_two) {

    private lateinit var linearLayoutFirst: LinearLayout
    private lateinit var linearLayoutSecond: LinearLayout
    private lateinit var textDimension: TextView
    private lateinit var textDimensionShowOne: TextView
    private lateinit var textDimensionShowTwo: TextView
    private var buttonsOne = Array(5) { arrayOfNulls<Button>(5) }
    private var buttonsTwo = Array(5) { arrayOfNulls<Button>(5) }
    private var destination = ""

    private lateinit var buttonEnter:Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonEnter = view.findViewById(R.id.buttonEnterTwoDimensions)
        linearLayoutFirst = view.findViewById(R.id.es_linearFigureDimensionsForTwoFirst)
        linearLayoutSecond = view.findViewById(R.id.es_linearFigureDimensionsSecond)
        buttonsOne = Array(5) { arrayOfNulls(5) }
        buttonsTwo = Array(5) { arrayOfNulls<Button>(5) }
        textDimension = view.findViewById(R.id.textViewDimensionsForTwoOperation)
        textDimensionShowOne = view.findViewById(R.id.textViewDimensionShowOne)
        textDimensionShowTwo = view.findViewById(R.id.textViewDimensionShowTwo)
        destination = FigureDimensionsForTwoArgs.fromBundle(requireArguments()).destination
        val gridLayoutOne = GridLayout(activity)
        gridLayoutOne.rowCount = 5
        val setPos = FragmentCalculate()
        gridLayoutOne.columnCount = 5
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                buttonsOne[i][j] = Button(activity)
                setPos.setPos(buttonsOne[i][j], i, j)
                gridLayoutOne.addView(buttonsOne[i][j])
            }
        }
        linearLayoutFirst.addView(gridLayoutOne)

        val gridLayoutTwo = GridLayout(activity)
        gridLayoutTwo.rowCount = 5
        gridLayoutTwo.columnCount = 5
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                buttonsTwo[i][j] = Button(activity)
                setPos.setPos(buttonsTwo[i][j], i, j)
                gridLayoutTwo.addView(buttonsTwo[i][j])
            }
        }
        linearLayoutSecond.addView(gridLayoutTwo)

        buttonsOne[0][0]!!.background.setTint(Color.parseColor("#FF9800"))
        buttonsOne[0][0]!!.isEnabled = false
        buttonsTwo[0][0]!!.background.setTint(Color.parseColor("#FF9800"))
        buttonsTwo[0][0]!!.isEnabled = false

        var rowOne = 0
        var columnOne = 0
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                val button = buttonsOne[i][j]
                button!!.id = i * 5 + j
                button.setOnClickListener {
                    rowOne = it.id / 5
                    columnOne = it.id % 5
                    toDefault(buttonsOne)
                    changeColorOffColumn(rowOne, columnOne, buttonsOne)
                    textDimensionShowOne.text = "${rowOne + 1} x ${columnOne + 1}"

                }
            }
        }
        var rowTwo = 0
        var columnTwo = 0
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                val button = buttonsTwo[i][j]
                button!!.id = i * 5 + j
                button.setOnClickListener {
                    rowTwo = it.id / 5
                    columnTwo = it.id % 5
                    toDefault(buttonsTwo)
                    changeColorOffColumn(rowTwo, columnTwo, buttonsTwo)
                    textDimensionShowTwo.text = "${rowTwo + 1} x ${columnTwo + 1}"
                }
            }
        }
        buttonEnter.setOnClickListener {
            if(destination == "MULTIPLY") {
                if (columnOne == rowTwo) {
                    val dimensions =
                        intArrayOf(columnOne + 1, rowOne + 1, columnTwo + 1, rowTwo + 1)

                    val action = FigureDimensionsForTwoDirections
                        .actionFigureDimensionsForTwoToFragmentMultiply(dimensions)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Dimensions are not correct",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                if (rowOne == columnOne) {
                    val criteriaForFindX = FigureDimensionsForTwoArgs
                        .fromBundle(requireArguments()).criteriaForFindX
                    if (criteriaForFindX == "<") {
                        if (columnOne == rowTwo) {
                            val dimensions =
                                intArrayOf(columnOne + 1, rowOne + 1, columnTwo + 1, rowTwo + 1)
                            val action = FigureDimensionsForTwoDirections
                                .actionFigureDimensionsForTwoToFragmentFindX(
                                    dimensions,
                                    criteriaForFindX
                                )
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Dimensions are not correct 1",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        if (columnTwo == rowOne) {
                            val dimensions =
                                intArrayOf(columnOne + 1, rowOne + 1, columnTwo + 1, rowTwo + 1)
                            val action = FigureDimensionsForTwoDirections
                                .actionFigureDimensionsForTwoToFragmentFindX(
                                    dimensions,
                                    criteriaForFindX
                                )
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Dimensions are not correct 2",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                else{
                    Toast.makeText(requireActivity(), "Dimensions are not correct 3", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun toDefault(buttons: Array<Array<Button?>>){
        for(i in 0 until 5){
            for(j in 0 until 5){
                buttons[i][j]!!.background.setTint(Color.GRAY)
            }
        }
    }
    fun changeColorOffColumn(row: Int, column: Int,  buttons: Array<Array<Button?>>){
        for(i in 0 .. row) {
            for (j in 0 .. column) {
                buttons[i][j]?.background?.setTint(Color.parseColor("#FF9800"))
            }
        }
    }
}