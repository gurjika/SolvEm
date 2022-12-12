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
import com.example.civa.MainActivity4
import com.example.civa.R

class FragmentCalculate: Fragment(R.layout.fragment_calculate) {
    private lateinit var linearLayout:LinearLayout
    private lateinit var textDimension: TextView
    private var buttons = Array(6) { arrayOfNulls<Button>(6) }
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button)
        linearLayout = view.findViewById(R.id.linearLayout)
        buttons = Array(6) { arrayOfNulls(6) }
        textDimension = view.findViewById(R.id.textView3)
        val gridLayout = GridLayout(activity)
        gridLayout.rowCount = 6
        gridLayout.columnCount = 6
        for (i in 0 until 6) {
            for (j in 0 until 6) {
                buttons[i][j] = Button(activity)
                setPos(buttons[i][j], i, j)
                gridLayout.addView(buttons[i][j])
            }
        }
        linearLayout.addView(gridLayout)
        configure()
        var text = ""
        buttons[0][0]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[0][1]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[1][0]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[1][1]?.background?.setTint(Color.parseColor("#FF9800"))
        buttons[1][1]?.isEnabled = false
        button.setOnClickListener {
            if(buttons[5][5]?.isEnabled == false){
                text = "6"

            }
            if(buttons[5][5]?.isEnabled == true && buttons[4][4]?.isEnabled == false){
                text = "5"

            }
            if(buttons[4][4]?.isEnabled == true && buttons[3][3]?.isEnabled == false){
                text = "4"

            }
            if(buttons[3][3]?.isEnabled == true && buttons[2][2]?.isEnabled == false){
                text = "3"

            }
            if(buttons[2][2]?.isEnabled == true && buttons[1][1]?.isEnabled == false){
                text = "2"

            }
            val intent = Intent(activity, MainActivity4::class.java)
            intent.putExtra("N", text)
            startActivity(intent)
        }
    }

    private fun setPos(button: Button?, row: Int, column: Int) {
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

    private fun turnOff(number: Int) {
        for (i in 0 until number) {
            buttons[number - 1][i]?.isEnabled = false
            buttons[i][number - 1]?.isEnabled = false
        }
    }

    private fun turnOn(number: Int) {
        for (i in 0 until number) {
            buttons[number - 1][i]?.isEnabled = true
            buttons[i][number - 1]?.isEnabled = true
        }
    }
    private fun changeColorOff(number: Int){
        for (i in 0 until number) {
            buttons[number - 1][i]?.background?.setTint(Color.parseColor("#FF9800"))
            buttons[i][number - 1]?.background?.setTint(Color.parseColor("#FF9800"))
        }
    }
    private fun changeColorOn(number: Int){
        for (i in 0 until number) {
            buttons[number - 1][i]?.background?.setTint(Color.GRAY)
            buttons[i][number - 1 ]?.background?.setTint(Color.GRAY)
        }
    }
    private fun configure(){
        for(i in 0..5){
            buttons[5][i]?.setOnClickListener {
                turnOff(6)
                changeColorOff(2)
                changeColorOff(1)
                changeColorOff(3)
                changeColorOff(4)
                changeColorOff(5)
                changeColorOff(6)
                turnOn(3)
                turnOn(2)
                turnOn(4)
                turnOn(5)
                textDimension.text = "6 x 6"
            }
            buttons[i][5]?.setOnClickListener {
                turnOff(6)
                turnOn(2)
                turnOn(3)
                turnOn(4)
                turnOn(5)
                changeColorOff(2)
                changeColorOff(1)
                changeColorOff(3)
                changeColorOff(4)
                changeColorOff(5)
                changeColorOff(6)
                textDimension.text = "6 x 6"
            }
        }
        for(i in 0..4){
            buttons[4][i]?.setOnClickListener {
                turnOn(6)
                turnOn(4)
                turnOn(3)
                turnOn(2)
                changeColorOn(6)
                turnOff(5)
                changeColorOff(3)
                changeColorOff(4)
                changeColorOff(5)
                changeColorOff(2)
                changeColorOff(1)
                textDimension.text = "5 x 5"
            }
            buttons[i][4]?.setOnClickListener {
                turnOff(5)
                turnOn(6)
                turnOn(4)
                turnOn(3)
                turnOn(2)
                changeColorOn(6)
                changeColorOff(3)
                changeColorOff(4)
                changeColorOff(5)
                changeColorOff(2)
                changeColorOff(1)
                textDimension.text = "5 x 5"
            }
        }
        for(i in 0.. 3){
            buttons[i][3]?.setOnClickListener {
                turnOn(2)
                turnOn(3)
                turnOn(5)
                turnOn(6)
                changeColorOn(5)
                changeColorOn(6)
                turnOff(4)
                changeColorOff(3)
                changeColorOff(4)
                changeColorOff(2)
                changeColorOff(1)
                textDimension.text = "4 x 4"
            }
            buttons[3][i]?.setOnClickListener {
                turnOn(2)
                turnOn(3)
                turnOn(5)
                turnOn(6)
                changeColorOn(5)
                changeColorOn(6)
                turnOff(4)
                changeColorOff(3)
                changeColorOff(4)
                changeColorOff(2)
                changeColorOff(1)
                textDimension.text = "4 x 4"
            }

        }
        for(i in 0..2){
            buttons[i][2]?.setOnClickListener{
                turnOn(2)
                turnOn(4)
                turnOn(5)
                turnOn(6)
                turnOff(3)
                changeColorOn(4)
                changeColorOn(5)
                changeColorOn(6)
                changeColorOff(3)
                changeColorOff(2)
                changeColorOff(1)
                textDimension.text = "3 x 3"
            }
            buttons[2][i]?.setOnClickListener{
                turnOn(2)
                turnOn(4)
                turnOn(5)
                turnOn(6)
                turnOff(3)
                changeColorOn(4)
                changeColorOn(5)
                changeColorOn(6)
                changeColorOff(3)
                changeColorOff(2)
                changeColorOff(1)
                textDimension.text = "3 x 3"
            }
        }
        for(i in 0..1){
            buttons[1][i]?.setOnClickListener {
                changeColorOn(2)
                changeColorOn(3)
                changeColorOn(4)
                changeColorOn(5)
                changeColorOn(6)
                changeColorOff(2)
                changeColorOff(1)
                turnOff(2)
                turnOn(3)
                turnOn(4)
                turnOn(5)
                turnOn(6)
                textDimension.text = "2 x 2"
            }
            buttons[i][1]?.setOnClickListener {
                changeColorOn(2)
                changeColorOn(3)
                changeColorOn(4)
                changeColorOn(5)
                changeColorOn(6)
                changeColorOff(2)
                changeColorOff(1)
                turnOff(2)
                turnOn(3)
                turnOn(4)
                turnOn(5)
                turnOn(6)
                textDimension.text = "2 x 2"
            }
        }
    }

}






