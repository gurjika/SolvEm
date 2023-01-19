package com.example.civa.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri.Builder
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.civa.*
import com.example.civa.R
import com.google.firebase.database.*
import java.math.BigDecimal
import java.math.RoundingMode

class FragmentFindX: Fragment(R.layout.fragment_find_x) {
    private lateinit var buttonCalculate: Button
    private lateinit var clear: Button
    private lateinit var linearFindXResult: LinearLayout
    private lateinit var esLinear: LinearLayout
    private lateinit var esLinear1: LinearLayout
    private var resultStringOne = ""
    private var resultStringTwo = ""
    private var comeFromHistory = false
    private var determinant = 0.0
    private lateinit var seeHowButton: Button
    private var array = Array(3) { DoubleArray(3) }
    private var array1 = Array(3) { DoubleArray(4) }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        esLinear = view.findViewById(R.id.es_linearFindX)
        esLinear1 = view.findViewById(R.id.es_linearFindX1)
        buttonCalculate = view.findViewById(R.id.buttonCalculateFindX)
        seeHowButton = view.findViewById(R.id.seeHowButtonFIndX)
        linearFindXResult = view.findViewById(R.id.linearFindXResult)
        seeHowButton.isEnabled = false
        clear = view.findViewById(R.id.buttonClearFindX)
        val dimensions = FragmentFindXArgs.fromBundle(requireArguments()).dimension
        val rowOne = dimensions[1]
        val columnOne = dimensions[0]
        val rowTwo = dimensions[3]
        val columnTwo = dimensions[2]
        val criteria = FragmentFindXArgs.fromBundle(requireArguments()).criteria
        comeFromHistory = FragmentFindXArgs.fromBundle(requireArguments()).comeFromHistory

        val editTexts1 = Array(rowOne) { arrayOfNulls<EditText>(columnOne) }
        val editTexts2 = Array(rowTwo) { arrayOfNulls<EditText>(columnTwo) }
        var textViews: Array<Array<TextView?>>
        val builder = BuilderTool()

        array = Array(rowOne) { DoubleArray(columnOne) }
        array1 = Array(rowTwo) { DoubleArray(columnTwo) }


        val gridLayout1 = GridLayout(requireActivity())

        gridLayout1.rowCount = rowOne
        gridLayout1.columnCount = columnOne
        val setPos = MakeGridLayout()
        for (i in 0 until rowOne) {
            for (j in 0 until columnOne) {
                editTexts1[i][j] = EditText(activity)
                setPos.setPosForEditText(editTexts1[i][j], i, j, 150)
                gridLayout1.addView(editTexts1[i][j])
            }
        }

        esLinear.addView(gridLayout1)
        val gridLayout2 = GridLayout(requireActivity())
        gridLayout2.rowCount = rowTwo
        gridLayout2.columnCount = columnTwo

        for (i in 0 until rowTwo) {
            for (j in 0 until columnTwo) {
                editTexts2[i][j] = EditText(activity)
                setPos.setPosForEditText(editTexts2[i][j], i, j, 150)
                gridLayout2.addView(editTexts2[i][j])
            }
        }
        esLinear1.addView(gridLayout2)

        clear.setOnClickListener {
            for(i in 0 until rowOne){
                for(j in 0 until columnOne){
                    editTexts1[i][j]!!.text = null
                    array[i][j] = 0.0
                }
            }
            for(i in 0 until rowTwo){
                for(j in 0 until columnTwo){
                    editTexts2[i][j]!!.text = null
                    array1[i][j] = 0.0
                }
            }
            esLinear.visibility = View.VISIBLE
            esLinear1.visibility = View.VISIBLE
            buttonCalculate.isEnabled = true
            linearFindXResult.visibility = View.INVISIBLE
        }




        database = FirebaseDatabase.getInstance().getReference("Users")
        sharedPreferences = this.requireActivity().getSharedPreferences(
            "MY_PREFS",
            Context.MODE_PRIVATE
        )
        val multiply = Multiply()
        val checkEditText = ValidateEditTexts()
        val checker = GetRidOfZeroes()


        if (comeFromHistory) {
            var index = 0
            val receivedMatrixOne = FragmentFindXArgs.fromBundle(requireArguments()).firstMatrix
            val receivedMatrixTwo = FragmentFindXArgs.fromBundle(requireArguments()).secondMatrix
            for (i in 0 until rowOne) {
                for (j in 0 until columnOne) {
                    editTexts1[i][j]!!.append(receivedMatrixOne!![index])
                    index++
                }
            }
            var indexTwo = 0
            for (i in 0 until rowTwo) {
                for (j in 0 until columnTwo) {
                    editTexts2[i][j]!!.append(receivedMatrixTwo!![indexTwo])
                    indexTwo++
                }
            }
        }

        buttonCalculate.setOnClickListener {
            resultStringOne = ""
            resultStringTwo = ""
            if (checkEditText.validateEm(editTexts1, editTexts1.size, editTexts1[0].size)) {
                for (i in 0 until editTexts1.size) {
                    for (j in 0 until editTexts1[0].size) {
                        array[i][j] = editTexts1[i][j]?.text.toString().toDouble()
                        resultStringOne = resultStringOne + array[i][j].toString() + ";"
                    }
                }
            }
            else {
                return@setOnClickListener
            }
            val findDeterminant = Determinant()

            if (array.size != 2) {
                for (i in 0 until array.size) {
                    findDeterminant.counter = 1
                    determinant += findDeterminant.determinant(i, 0, 0, array, array.size)
                }
            }
            else{
                determinant = array[0][0] * array[1][1] - array[0][1] * array[1][0]
            }

            if(determinant == 0.0){
                Toast.makeText(requireActivity(), "შებრუნებული მატრიცა არ არსებობს", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }




            if (checkEditText.validateEm(editTexts2, editTexts2.size, editTexts2[0].size)) {
                for (i in 0 until editTexts2.size) {
                    for (j in 0 until editTexts2[0].size) {
                        array1[i][j] = editTexts2[i][j]?.text.toString().toDouble()
                        resultStringTwo = resultStringTwo + array1[i][j].toString() + ";"

                    }
                }
            }
            else {
                return@setOnClickListener
            }
                buttonCalculate.isEnabled = false
                esLinear.visibility = View.INVISIBLE
                esLinear1.visibility = View.INVISIBLE

                if (criteria == "<") {
                    resultStringOne = "$resultStringOne$rowOne;$columnOne;<;FINDX"
                    resultStringTwo = "$resultStringTwo$rowTwo;$columnTwo;FINDX"
                } else {
                    resultStringOne = "$resultStringOne$rowOne;$columnOne;>;FINDX"
                    resultStringTwo = "$resultStringTwo$rowTwo;$columnTwo;FINDX"
                }


                val mikavshirebuli: Array<DoubleArray>

                val result: Array<DoubleArray>

                mikavshirebuli = Array(array.size) { DoubleArray(array.size) }
                if (array.size != 2) {

                    for (i in 0 until array.size) {
                        for (j in 0 until array.size) {
                            findDeterminant.counter = 0
                            mikavshirebuli[i][j] =
                                findDeterminant.determinant(i, j, 0, array, array.size)
                        }
                    }

                } else {
                    mikavshirebuli[0][0] = array[1][1]
                    mikavshirebuli[0][1] = -array[0][1]
                    mikavshirebuli[1][0] = -array[1][0]
                    mikavshirebuli[1][1] = array[0][0]
                }

                if (criteria == "<") {
                    result = multiply.multiplyEm(mikavshirebuli, array1)
                    textViews = Array(rowOne) { arrayOfNulls<TextView>(columnTwo) }
                } else {
                    result = multiply.multiplyEm(array1, mikavshirebuli)
                    textViews = Array(rowTwo) { arrayOfNulls<TextView>(columnOne) }
                }
                if (!comeFromHistory) {
                    builder.uploadMatrix(
                        requireActivity(),
                        database,
                        resultStringOne,
                        resultStringTwo
                    )
                }

                val resultGridLayout = GridLayout(requireActivity())
                resultGridLayout.rowCount = result.size
                resultGridLayout.columnCount = result[0].size

                val reduce = ReduceFraction()



                for (i in 0 until result.size) {
                    for (j in 0 until result[0].size) {
                        textViews[i][j] = TextView(requireActivity())
                        if (checker.noZeroes(determinant.toString()) == determinant.toString()
                            || checker.noZeroes(result[i][j].toString()) == result[i][j].toString()
                        ) {
                            textViews[i][j]!!.text =
                                BigDecimal(result[i][j] / determinant)
                                    .setScale(2, RoundingMode.HALF_DOWN).toString()
                        } else {
                            textViews[i][j]!!.text =
                                reduce.asFraction(result[i][j].toInt(), determinant.toInt())
                        }
                        setPos.setPosForText(textViews[i][j], i, j, 150)
                        resultGridLayout.addView(textViews[i][j])
                    }
                }
                linearFindXResult.removeAllViews()
                linearFindXResult.addView(resultGridLayout)
            }
        }

}