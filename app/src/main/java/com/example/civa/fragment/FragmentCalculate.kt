package com.example.civa.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class FragmentCalculate: Fragment(R.layout.fragment_calculate) {
    private lateinit var linearLayout:LinearLayout
    private lateinit var textDimension: TextView
    private var buttons = Array(5) { arrayOfNulls<Button>(5) }
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button)
        linearLayout = view.findViewById(R.id.linearLayout)
        buttons = Array(5) { arrayOfNulls(5) }
        textDimension = view.findViewById(R.id.textView3)
        val gridLayout = GridLayout(activity)
        gridLayout.rowCount = 5



        gridLayout.columnCount = 5
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                buttons[i][j] = Button(activity)
                setPos(buttons[i][j], i, j)
                gridLayout.addView(buttons[i][j])
            }
        }
        linearLayout.addView(gridLayout)


        buttons[0][0]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[0][1]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[1][0]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[1][1]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[0][0]?.isEnabled = false

        val destination = FragmentCalculateArgs.fromBundle(requireArguments()).destination
        val flexibleCalculate = FigureDimensionsForTwo()
        var sendDimension = 0
        var sendDimensionAsString = ""
        if(destination == "DETERMINANT" || destination == "INVERSE") {
            for (i in 0 until 5) {
                for (j in 0 until 5) {
                    val button = buttons[i][j]
                    button!!.id = i * 5 + j
                    button.setOnClickListener {
                        val row = it.id / 5
                        val column = it.id % 5
                        toDefault()

                        if (row >= column) {
                            changeColorOff(row)
                            textDimension.text = "${row + 1} x ${row + 1}"
                            sendDimension = row + 1
                        } else {
                            changeColorOff(column)
                            textDimension.text = "${column + 1} x ${column + 1}"
                            sendDimension = column + 1

                        }
                    }
                }
            }
        }
        else {

            for (i in 0 until 5) {
                for (j in 0 until 5) {
                    val button = buttons[i][j]
                    button!!.id = i * 5 + j
                    button.setOnClickListener {
                        val rowOne = it.id / 5
                        val columnOne = it.id % 5
                        flexibleCalculate.toDefault(buttons)
                        flexibleCalculate.changeColorOffColumn(rowOne, columnOne, buttons)
                        textDimension.text = "${rowOne + 1} x ${columnOne + 1}"
                        sendDimensionAsString = "${rowOne + 1}${columnOne + 1}"

                    }
                }
            }
        }
            button.setOnClickListener {
                val destination = FragmentCalculateArgs.fromBundle(requireArguments()).destination

                if(destination == "INVERSE"){
                    val action = FragmentCalculateDirections
                        .actionFragmenCalculateToFragmentInverse(sendDimension)
                    findNavController().navigate(action)
                }
                else if(destination == "DETERMINANT"){
                    val action = FragmentCalculateDirections
                        .actionFragmenCalculateToFragmentDeterminant(sendDimension, null, false)
                    findNavController().navigate(action)
                }
                else if(destination == "T"){

                }
                else if(destination == "ADD"){
                    val action = FragmentCalculateDirections
                        .actionFragmenCalculateToFragmentAddOrSubtract("$sendDimensionAsString+", null, null,false)
                    findNavController().navigate(action)
                }
                else if(destination == "SUBTRACT"){


                    val action = FragmentCalculateDirections
                        .actionFragmenCalculateToFragmentAddOrSubtract(
                            "$sendDimensionAsString-", null, null, false)

                    findNavController().navigate(action)
                }
            }
        }

        fun setPos(button: Button?, row: Int, column: Int) {
            val param = GridLayout.LayoutParams()
            param.width = 70
            param.height = 70
            param.setGravity(Gravity.CENTER)
            param.rowSpec = GridLayout.spec(row)
            param.columnSpec = GridLayout.spec(column)
            button!!.layoutParams = param
            button.gravity = Gravity.CENTER
            button.setTextColor(Color.WHITE)
            button.background.setTint(Color.GRAY)
        }


    private fun changeColorOff(size:Int){
        for(i in 0 .. size) {
            for (j in 0 .. size) {
                buttons[i][j]?.background?.setTint(Color.parseColor("#FF9800"))
            }
        }
    }
        private fun toDefault(){
            for(i in 0 until 5){
                for(j in 0 until 5){
                    buttons[i][j]!!.background.setTint(Color.GRAY)
                }
            }
        }
}







