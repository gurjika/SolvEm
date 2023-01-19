package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.civa.MakeGridLayout
import com.example.civa.R
import com.example.civa.ValidateEditTexts
import com.google.firebase.database.*

class FragmentAddOrSubtract:Fragment(R.layout.fragment_add_or_subtract) {
    private lateinit var buttonCalculate: Button
    private lateinit var clear: Button
    private lateinit var linearAddOrSubtractResult: LinearLayout
    private lateinit var esLinear: LinearLayout
    private lateinit var esLinear1: LinearLayout
    private var resultStringOne = ""
    private var resultStringTwo = ""
    private var numberOfColumns = 4
    private lateinit var seeHowButton: Button
    private var array = Array(4) { DoubleArray(4) }
    private var array1 = Array(4) { DoubleArray(4) }
    private var numberOfRows = 4
    private var comeFromHistory = false
    private  var dimension = 4
    private lateinit var operationTextView: TextView
    private var operation = ""
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCalculate = view.findViewById(R.id.buttonAddOrSubtractCalculate)
        seeHowButton = view.findViewById(R.id.buttonAddOrSubtractSeeHow)
        clear = view.findViewById(R.id.buttonAddOrSubtractClear)
        linearAddOrSubtractResult = view.findViewById(R.id.es_linearAddOrSubtractResult)
        esLinear = view.findViewById(R.id.es_linearAddOrSubtractFirst)
        esLinear1 = view.findViewById(R.id.es_linearAddOrSubtractSecond)
        operationTextView = view.findViewById(R.id.textViewAddOrSubtractOr)


        val receiving = FragmentAddOrSubtractArgs.fromBundle(requireArguments()).dimension
        operation = receiving[2].toString()
        numberOfColumns = receiving[1].toString().toInt()
        numberOfRows = receiving[0].toString().toInt()
        val builder = BuilderTool()
        comeFromHistory = FragmentAddOrSubtractArgs.fromBundle(requireArguments()).comeFromHistory

        seeHowButton.isEnabled = false

         array = Array(numberOfRows) { DoubleArray(numberOfColumns) }
         array1 = Array(numberOfRows) { DoubleArray(numberOfColumns) }

        val editTexts1 = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val editTexts2 = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val textViews = Array(numberOfRows) { arrayOfNulls<TextView>(numberOfColumns) }
        operationTextView.text = operation
        val gridLayout1 = builder.buildGrid(
            requireActivity(),
            editTexts1,
            numberOfRows,
            numberOfColumns)


        val setPos = MakeGridLayout()

        esLinear.addView(gridLayout1)
        val gridLayout2 = builder.buildGrid(
            requireActivity(),
            editTexts2,
            numberOfRows,
            numberOfColumns
        )
        esLinear1.addView(gridLayout2)

        if(comeFromHistory){

            val receivedArrayFirst = FragmentAddOrSubtractArgs.fromBundle(requireArguments()).matrixFirst
            val receivedArraySecond = FragmentAddOrSubtractArgs.fromBundle(requireArguments()).matrixSecond
            var index = 0
            for(i in 0 until numberOfRows){
                for(j in 0 until numberOfColumns){
                    editTexts1[i][j]!!.append(receivedArrayFirst!![index])
                    editTexts2[i][j]!!.append(receivedArraySecond!![index])
                    index++
                }
            }
        }

        val checkEditText = ValidateEditTexts()
        val checker = GetRidOfZeroes()

        clear.setOnClickListener {
            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {
                    editTexts1[i][j]?.text = null
                    editTexts2[i][j]!!.text = null
                    array[i][j] = 0.0
                    array1[i][j] = 0.0

                }
            }
            esLinear1.visibility = View.VISIBLE
            esLinear.visibility = View.VISIBLE
            linearAddOrSubtractResult.visibility = View.INVISIBLE
            buttonCalculate.isEnabled = true
        }


        buttonCalculate.setOnClickListener {

            if(!builder.checkInternet(requireActivity())){
                Toast.makeText(requireActivity(), "inte ar ari", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            resultStringOne = ""
            resultStringTwo = ""

            if (checkEditText.validateEm(editTexts1, editTexts1.size, editTexts1[0].size)) {
                for (i in 0 until editTexts1.size) {
                    for (j in 0 until editTexts1[0].size) {
                        array[i][j] = editTexts1[i][j]?.text.toString().toDouble()
                        resultStringOne = resultStringOne + checker.noZeroes(array[i][j].toString()) + ";"
                    }
                }
            }
            else{
                return@setOnClickListener
            }

            if (checkEditText.validateEm(editTexts2, editTexts2.size, editTexts2[0].size)) {
                for (i in 0 until editTexts2.size) {
                    for (j in 0 until editTexts2[0].size) {
                        array1[i][j] = editTexts2[i][j]?.text.toString().toDouble()
                        resultStringTwo = resultStringTwo + checker.noZeroes(array[i][j].toString()) + ";"
                    }
                }
            }
            else{
                return@setOnClickListener
            }


            esLinear.visibility = View.INVISIBLE
            esLinear1.visibility = View.INVISIBLE

            buttonCalculate.isEnabled = false
            linearAddOrSubtractResult.visibility = View.VISIBLE

            val resultGridLayout = GridLayout(requireActivity())
            resultGridLayout.rowCount = numberOfRows
            resultGridLayout.columnCount = numberOfColumns
            var toSendOperation = ""
            if(operation == "+"){
                toSendOperation = "ADD"
            }
            else if(operation == "-"){
                toSendOperation = "SUBTRACT"
            }


            database = FirebaseDatabase.getInstance().getReference("Users")


            resultStringOne = resultStringOne + numberOfRows.toString()  +
                    ";" + numberOfColumns.toString() + ";" + operation + ";" + toSendOperation
            resultStringTwo = resultStringTwo + numberOfRows.toString()  +
                    ";" + numberOfColumns.toString() + ";" + toSendOperation

            if(!comeFromHistory) {
                builder.uploadMatrix(
                    requireActivity(),
                    database,
                    resultStringOne,
                    resultStringTwo
                )
            }
        val checker = GetRidOfZeroes()

        if(operation == "+") {
            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {
                    textViews[i][j] = TextView(requireActivity())
                    textViews[i][j]!!.text = checker.noZeroes((array[i][j] + array1[i][j]).toString())
                    setPos.setPosForText(textViews[i][j], i, j, 100)
                    resultGridLayout.addView(textViews[i][j])

                }
            }
        }
            else{
                for (i in 0 until numberOfRows) {
                    for (j in 0 until numberOfColumns) {
                        textViews[i][j] = TextView(requireActivity())
                        textViews[i][j]!!.text = checker.noZeroes((array[i][j] - array1[i][j]).toString())
                        setPos.setPosForText(textViews[i][j], i, j, 100)
                        resultGridLayout.addView(textViews[i][j])
                    }
                }
            }
            linearAddOrSubtractResult.removeAllViews()
            linearAddOrSubtractResult.addView(resultGridLayout)
        }
    }
}