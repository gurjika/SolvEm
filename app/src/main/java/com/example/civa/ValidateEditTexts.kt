package com.example.civa

import android.widget.EditText

class ValidateEditTexts {
    var everyThingAlright = true
    fun validateEm(editTexts: Array<Array<EditText?>>, n: Int, m:Int):Boolean {
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (editTexts[i][j]?.text!!.isEmpty()) {
                    editTexts[i][j]?.error = "Incorrect number"
                    everyThingAlright = false
                    return everyThingAlright
                } else if (editTexts[i][j]!!.text.toString() == "-") {
                    editTexts[i][j]?.error = "Incorrect number"
                    everyThingAlright = false
                    return everyThingAlright
                } else if (editTexts[i][j]!!.text.toString() == "." ||
                    editTexts[i][j]!!.text.toString() == ".0"
                ) {
                    editTexts[i][j]?.error = "Incorrect number"
                    everyThingAlright = false
                    return everyThingAlright
                }
            }
        }
        return everyThingAlright
    }
}