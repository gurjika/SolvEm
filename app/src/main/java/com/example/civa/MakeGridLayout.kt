package com.example.civa

import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import com.zanvent.mathview.MathView

class MakeGridLayout {

     fun setPosForEditText(editText: EditText?, row: Int, column: Int, width: Int) {
        val param = GridLayout.LayoutParams()
        param.width = width
        param.height = width
        param.setGravity(Gravity.CENTER)
        param.columnSpec = GridLayout.spec(column)
        param.rowSpec = GridLayout.spec(row)
        editText!!.layoutParams = param
        editText.gravity = Gravity.CENTER
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        editText.setTextColor(Color.BLACK)
    }


    fun setPosForText(textView: TextView?, row: Int, column: Int, width:Int) {
        val param = GridLayout.LayoutParams()
        param.width = width
        param.height = width
        param.setGravity(Gravity.CENTER)
        param.columnSpec = GridLayout.spec(column)
        param.rowSpec = GridLayout.spec(row)
        textView!!.layoutParams = param
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.BLACK)
    }



}