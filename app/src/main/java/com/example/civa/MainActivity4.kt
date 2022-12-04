package com.example.civa


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.*
import android.view.Gravity
import android.view.Gravity.CENTER
import android.widget.*
import androidx.appcompat.app.ActionBar


class MainActivity4 : AppCompatActivity() {
    private lateinit var result:TextView
    private lateinit var button1:Button
    private lateinit var linear:LinearLayout
    private var n = 0
    private var numberOfColumns = 5
    private var array = Array(5) { DoubleArray(5) }
    private var saveArray = Array(5) { DoubleArray(5) }
    private var numberOfRows = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        result = findViewById(R.id.textView)
        button1  = findViewById(R.id.button2)
        linear = findViewById(R.id.es_linear)
        val aba = intent!!.extras!!.getString("N").toString().toInt()
        n = aba
        numberOfRows = n
        numberOfColumns = n
        array = Array(n) { DoubleArray(n) }
        saveArray = Array(n) { DoubleArray(n) }
        val editTexts = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val gridLayout = GridLayout(this)


        gridLayout.rowCount = numberOfRows
        gridLayout.columnCount = numberOfColumns
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfColumns) {
                editTexts[i][j] = EditText(this)
                setPos(editTexts[i][j], i, j)
                gridLayout.addView(editTexts[i][j])
            }
        }
        linear.addView(gridLayout)


        button1.setOnClickListener {
            for(i in 0 until n){
                for(j in 0 until n){
                    if(editTexts[i][j]?.text!!.isEmpty()){
                        editTexts[i][j]?.error = "Incorrect number"
                        return@setOnClickListener
                    }
                    else if(editTexts[i][j]!!.text.toString() == "-"){
                        editTexts[i][j]?.error = "Incorrect number"
                        return@setOnClickListener
                    }
                    else if(editTexts[i][j]!!.text.toString() == "." ||
                        editTexts[i][j]!!.text.toString() == ".0"){
                        editTexts[i][j]?.error = "Incorrect number"
                        return@setOnClickListener
                    }
                    array[i][j] = editTexts[i][j]?.text.toString().toDouble()
                    aba()
                }
            }
        }
    }


    private fun setPos(editText: EditText?, row: Int, column: Int) {
        val param = GridLayout.LayoutParams()

        param.width = 100
        param.height = 100
        param.setGravity(Gravity.CENTER)
        param.rowSpec = GridLayout.spec(row)
        param.columnSpec = GridLayout.spec(column)
        editText!!.layoutParams = param
        editText.gravity = CENTER
        editText.setTextColor(Color.WHITE)
        editText.inputType =  TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL or TYPE_NUMBER_FLAG_SIGNED
        editText.setTextColor(Color.BLACK)
    }
    private fun aba() {
        var sum = 0.0
        if(n == 2){
            val result2x2 = array[0][0] * array[1][1] - array[1][0]*array[0][1]
            if (sum.toString().contains(".0")) {
                result.text = "RESULT: ${result2x2.toString().dropLast(2)}"
            }
            else {
                result.text = "RESULT: $result2x2"
            }
        }
        else {
            for (i in 0 until n) {
                sum += determinant(i, 0, 0, saveArray)
            }
            if (sum.toString().contains(".0")) {
                result.text = "RESULT: ${sum.toString().dropLast(2)}"
            } else {
                result.text = "RESULT: $sum"
            }
        }
    }
    private fun determinant(row:Int, column:Int, needed:Int, Array:Array<DoubleArray>):Double{

        var parent = 0.0

        val saveArray = Array((n - 1) - needed){DoubleArray((n - 1)- needed)}
        if(needed == 0){
            parent = array[column][row]
        }
        if(needed > 0){
            parent = Array[column][row]
        }
        if((row + column) % 2 !=0){
            parent = -parent
        }
        val temporary:MutableList<Double> = mutableListOf()
        var e = 0
        for (i in 0.. (n - 1) - needed) {
            if (column == i) {
                continue
            }
            for (m in 0..(n - 1) - needed) {
                if(needed > 0 && row == n - needed - 1) {
                    if(row == m) {
                        continue
                    }
                }
                if(row == m){
                    continue
                }
                if(needed == 0){
                    temporary.add(array[i][m])
                }
                if(needed !=0){
                    temporary.add(Array[i][m])
                }
            }
        }
        for(i in 0..(n - 2) - needed){
            for (m in 0..(n - 2) - needed){
                saveArray[i][m] = temporary[e]
                e++
            }
        }
        val needed1 = needed + 1
        if(temporary.size == 4) {
            return parent*(saveArray[0][0]*saveArray[1][1]-saveArray[1][0]*saveArray[0][1])
        }
        else {
            var sum = 0.0
            for (i in 0..(n - 1) - needed1) {
                sum += parent * (determinant(i, column, needed1, saveArray))
            }
            return sum
        }
    }
}