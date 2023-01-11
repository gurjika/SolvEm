package com.example.civa

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.fragment.Display
import com.example.civa.fragment.DisplayAlgebrals
import com.example.civa.fragment.GetRidOfZeroes
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

        comeFromHistory = FragmentAddOrSubtractArgs.fromBundle(requireArguments()).comeFromHistory



         array = Array(numberOfRows) { DoubleArray(numberOfColumns) }
         array1 = Array(numberOfRows) { DoubleArray(numberOfColumns) }

        val editTexts1 = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val editTexts2 = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val textViews = Array(numberOfRows) { arrayOfNulls<TextView>(numberOfColumns) }
        operationTextView.text = operation
        val gridLayout1 = GridLayout(requireActivity())

        gridLayout1.rowCount = numberOfRows
        gridLayout1.columnCount = numberOfColumns
        val setPos = MakeGridLayout()
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfColumns) {
                editTexts1[i][j] = EditText(activity)
                setPos.setPosForEditText(editTexts1[i][j], i, j, 70)
                gridLayout1.addView(editTexts1[i][j])
            }
        }
        esLinear.addView(gridLayout1)
        val gridLayout2 = GridLayout(requireActivity())
        gridLayout2.rowCount = numberOfRows
        gridLayout2.columnCount = numberOfColumns

        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfColumns) {
                editTexts2[i][j] = EditText(activity)
                setPos.setPosForEditText(editTexts2[i][j], i, j, 70)
                gridLayout2.addView(editTexts2[i][j])
            }
        }
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


        clear.setOnClickListener {
            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {
                    editTexts1[i][j]?.text = null
                    editTexts2[i][j]!!.text = null
                    array[i][j] = 0.0
                    array1[i][j] = 0.0
                    esLinear1.visibility = View.VISIBLE
                    esLinear.visibility = View.VISIBLE
                    linearAddOrSubtractResult.visibility = View.GONE
                }
            }
        }


        buttonCalculate.setOnClickListener {

            esLinear.visibility = View.GONE
            esLinear1.visibility = View.GONE
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
            sharedPreferences = this.requireActivity().getSharedPreferences(
                "MY_PREFS",
                Context.MODE_PRIVATE
            )

            resultStringOne = resultStringOne + numberOfColumns.toString()  +
                    ";" + numberOfRows.toString() + ";" + operation + ";" + toSendOperation
            resultStringTwo = resultStringTwo + numberOfColumns.toString()  +
                    ";" + numberOfRows.toString() + ";" + toSendOperation

            if(!comeFromHistory) {
                val valueEventListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val currentValue = dataSnapshot.child("0").value.toString().toInt()
                        val updatedValue = currentValue + 1
                        database.child("email").child("0").setValue(currentValue + 2)
                        database.child("email").child(updatedValue.toString())
                            .setValue(resultStringOne)
                        database.child("email").child((updatedValue + 1).toString())
                            .setValue(resultStringTwo)
                        sharedPreferences.edit()
                            .putString(updatedValue.toString(), resultStringOne)
                            .putString((updatedValue + 1).toString(), resultStringTwo)
                            .apply()

                        Toast.makeText(requireActivity(), "added", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(requireActivity(), "noooooooo", Toast.LENGTH_SHORT).show()
                    }
                }
                database.child("email").addListenerForSingleValueEvent(valueEventListener)
            }


        if(operation == "+") {
            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {
                    textViews[i][j] = TextView(requireActivity())
                    textViews[i][j]!!.text = (array[i][j] + array1[i][j]).toString()
                    setPos.setPosForText(textViews[i][j], i, j, 100)
                    resultGridLayout.addView(textViews[i][j])

                }
            }
        }
            else{
                for (i in 0 until numberOfRows) {
                    for (j in 0 until numberOfColumns) {
                        textViews[i][j] = TextView(requireActivity())
                        textViews[i][j]!!.text = (array[i][j] - array1[i][j]).toString()
                        setPos.setPosForText(textViews[i][j], i, j, 100)
                        resultGridLayout.addView(textViews[i][j])
                    }
                }
            }
            linearAddOrSubtractResult.addView(resultGridLayout)
        }
    }
}