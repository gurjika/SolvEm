package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.*
import com.example.civa.R
import com.google.firebase.database.*

class FragmentMultiply:Fragment(R.layout.fragment_multiply) {
    private lateinit var buttonCalculate: Button
    private lateinit var clear: Button
    private lateinit var linearMultiplyResult: LinearLayout
    private lateinit var esLinear: LinearLayout
    private lateinit var esLinear1: LinearLayout
    private var resultStringOne = ""
    private var resultStringTwo = ""
    private var comeFromHistory = false
    private lateinit var seeHowButton: Button
    private var array = Array(3) { DoubleArray(3) }
    private var array1 = Array(3) { DoubleArray(4) }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCalculate = view.findViewById(R.id.buttonCalculateMultiply)
        seeHowButton = view.findViewById(R.id.seeHowButtonMultiply)
        clear = view.findViewById(R.id.buttonClearMultiply)
        linearMultiplyResult = view.findViewById(R.id.linearMultiplyResult)
        esLinear = view.findViewById(R.id.es_linearMultiply)
        esLinear1 = view.findViewById(R.id.es_linearMultiply1)

        val dimensions = FragmentMultiplyArgs.fromBundle(requireArguments()).dimensions
        val rowOne = dimensions[1]
        val columnOne = dimensions[0]
        val rowTwo = dimensions[3]
        val columnTwo = dimensions[2]
        val builder = BuilderTool()


        comeFromHistory = FragmentMultiplyArgs.fromBundle(requireArguments()).comeFromHistory

        val editTexts1 = Array(rowOne) { arrayOfNulls<EditText>(columnOne) }
        val editTexts2 = Array(rowTwo) { arrayOfNulls<EditText>(columnTwo) }
        val textViews = Array(rowOne) { arrayOfNulls<TextView>(columnTwo) }

        val setPos = MakeGridLayout()
        array = Array(rowOne) { DoubleArray(columnOne) }
        array1 = Array(rowTwo) { DoubleArray(columnTwo) }

        val gridLayout1 = builder.buildGrid(
            requireActivity(),
            editTexts1,
            rowOne,
            columnOne
        )
        esLinear.addView(gridLayout1)
        val gridLayout2 = builder.buildGrid(
            requireActivity(),
            editTexts2,
            rowTwo,
            columnTwo
        )

        esLinear1.addView(gridLayout2)

        if(comeFromHistory){
            var index = 0
            val receivedMatrixOne = FragmentMultiplyArgs.fromBundle(requireArguments()).firstMatrix
            val receivedMatrixTwo = FragmentMultiplyArgs.fromBundle(requireArguments()).secondMatrix
            for(i in 0 until rowOne){
                for(j in 0 until columnOne){
                    editTexts1[i][j]!!.append(receivedMatrixOne!![index])
                    index++
                }
            }
            var indexTwo = 0
            for(i in 0 until rowTwo){
                for(j in 0 until columnTwo){
                    editTexts2[i][j]!!.append(receivedMatrixTwo!![indexTwo])
                    indexTwo++
                }
            }
        }



        database = FirebaseDatabase.getInstance().getReference("Users")
        sharedPreferences = this.requireActivity().getSharedPreferences(
            "MY_PREFS",
            Context.MODE_PRIVATE
        )
        val multiply = Multiply()
        val checkEditText = ValidateEditTexts()
        val checker = GetRidOfZeroes()

        buttonCalculate.setOnClickListener {
            resultStringOne = ""
            resultStringTwo = ""
            for (i in 0 until editTexts1.size) {
                for (j in 0 until editTexts1[0].size) {
                    if (checkEditText.validateEm(editTexts1, editTexts1.size, editTexts1[0].size)) {
                        array[i][j] = editTexts1[i][j]?.text.toString().toDouble()
                        resultStringOne = resultStringOne + array[i][j].toString() + ";"

                    } else {
                        return@setOnClickListener
                    }
                }
            }


            for (i in 0 until editTexts2.size) {
                for (j in 0 until editTexts2[0].size) {
                    if (checkEditText.validateEm(editTexts2, editTexts2.size, editTexts2[0].size)) {
                        array1[i][j] = editTexts2[i][j]?.text.toString().toDouble()

                        resultStringTwo = resultStringTwo + array1[i][j].toString() + ";"
                    } else {
                        return@setOnClickListener
                    }
                }
            }


            resultStringOne = "$resultStringOne$rowOne;$columnOne;x;MULTIPLY"
            resultStringTwo =  "$resultStringTwo$rowTwo;$columnTwo;MULTIPLY"

            if(!comeFromHistory){
                builder.uploadMatrix(
                    requireActivity(),
                    database,
                    sharedPreferences,
                    resultStringOne,
                    resultStringTwo)
            }



            val result = multiply.multiplyEm(array, array1)


            val resultGridLayout = GridLayout(requireActivity())
            resultGridLayout.rowCount = rowOne
            resultGridLayout.columnCount = columnTwo




            for (i in 0 until result.size) {
                for (j in 0 until result[0].size) {
                    textViews[i][j] = TextView(requireActivity())
                    textViews[i][j]!!.text = checker.noZeroes(result[i][j].toString())
                    setPos.setPosForText(textViews[i][j], i, j, 100)
                    resultGridLayout.addView(textViews[i][j])
                }
            }
            linearMultiplyResult.addView(resultGridLayout)

        }
    }
}




