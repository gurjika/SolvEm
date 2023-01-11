package com.example.civa.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.*
import com.example.civa.R
import com.google.firebase.database.*
import com.zanvent.mathview.MathView
import java.math.BigDecimal
import java.math.RoundingMode

class FragmentInverse: Fragment(R.layout.fragment_inverse) {

    private lateinit var result: TextView
    private lateinit var buttonCalculate: Button
    private lateinit var clear: Button
    private lateinit var linear: LinearLayout
    private var n = 4
    var comeFromHistory = false
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    private var determinant = 0.0
    private var resultString =""
    private lateinit var linearInverseResult: LinearLayout
    private var oldParent = 0.0
    private var oldNewParent = 0.0
    private lateinit var adapter: Display
    private lateinit var resultLinearLayout: LinearLayout
    private var parentArray: MutableList<DisplayAlgebrals> = mutableListOf()
    private var sumArray: MutableList<Double> = mutableListOf()
    private var numberOfColumns = 4
    private var resultStringSet = linkedSetOf<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var seeHowButton: Button


    private var tablelist = mutableListOf<Table>()
    private var toAddInTableList = mutableListOf<GridLayout>()
    private var array = Array(4) { DoubleArray(4) }
    private var saveArray = Array(5) { DoubleArray(5) }
    private var numberOfRows = 4



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.textViewInverse)
        buttonCalculate = view.findViewById(R.id.buttonCalculateInverse)
        linear = view.findViewById(R.id.es_linearInverse)
        val dimension = FragmentInverseArgs.fromBundle(requireArguments()).dimension

        numberOfColumns = dimension
        numberOfRows = dimension
        n = dimension
        recyclerView = view.findViewById(R.id.recyclerViewDeterminantInverse)
        seeHowButton = view.findViewById(R.id.seeHowButtonInverse)
        clear = view.findViewById(R.id.buttonClearInverse)
        linearInverseResult = view.findViewById(R.id.linearResultIneverse)
        val editTexts = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val textViews = Array(numberOfRows) { arrayOfNulls<TextView>(numberOfColumns) }
        val gridLayout = GridLayout(activity)
        val setPos = MakeGridLayout()
        array = Array(n) { DoubleArray(n) }
        comeFromHistory = FragmentInverseArgs.fromBundle(requireArguments()).comeFromHistory
        gridLayout.rowCount = numberOfRows
        gridLayout.columnCount = numberOfColumns
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfColumns) {
                editTexts[i][j] = EditText(activity)
                setPos.setPosForEditText(editTexts[i][j], i, j, 100)
                gridLayout.addView(editTexts[i][j])
            }
        }
        linear.addView(gridLayout)

        if(comeFromHistory) {
            val receivedArray = FragmentInverseArgs.fromBundle(requireArguments()).sendingArray
            val row = receivedArray!!.last().toInt()
            val column = receivedArray[receivedArray.size - 2].toInt()
            var index = 0
            for (i in 0 until row) {
                for (j in 0 until column) {
                    editTexts[i][j]!!.append(receivedArray[index])
                    index++
                }
            }
        }
        val gridLayoutForResult = GridLayout(requireActivity())

        gridLayoutForResult.rowCount = numberOfRows
        gridLayoutForResult.columnCount = numberOfColumns

        val checkEditText = ValidateEditTexts()
        var toAdd = ""

        buttonCalculate.setOnClickListener {
            for (i in 0 until n) {
                for (j in 0 until n) {
                    if (checkEditText.validateEm(editTexts, n, n)) {
                        array[i][j] = editTexts[i][j]?.text.toString().toDouble()
                        resultString = resultString + array[i][j].toString() + ";"
                    } else {
                        resultStringSet.clear()
                        resultString = ""
                        return@setOnClickListener
                    }
                }

            }

            resultString = "$resultString$n;$n;INVERSE"

            database = FirebaseDatabase.getInstance().getReference("Users")
            sharedPreferences = this.requireActivity().getSharedPreferences("MY_PREFS", MODE_PRIVATE)

            var counter = 0

            if(!comeFromHistory) {
                val valueEventListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val currentValue = dataSnapshot.child("0").value.toString().toInt()
                        val updatedValue = currentValue + 1
                        database.child("email").child("0").setValue(currentValue + 1)
                        database.child("email").child(updatedValue.toString())
                            .setValue(resultString)

                        sharedPreferences.edit()
                            .putString(updatedValue.toString(), resultString)
                            .apply()
                        Toast.makeText(requireActivity(), "added", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(requireActivity(), "noooooooo", Toast.LENGTH_SHORT).show()
                    }
                }
                database.child("email").addListenerForSingleValueEvent(valueEventListener)
            }




            calculate()

            var index = 0
            val checker = ReduceFraction()
            val check = GetRidOfZeroes()

            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {


                    textViews[i][j] = TextView(requireActivity())
                    setPos.setPosForText(textViews[i][j], i, j, 100)
                    if(check.noZeroes(determinant.toString()) == determinant.toString()
                        || check.noZeroes(sumArray[index].toString()) == sumArray[index].toString()){
                        textViews[i][j]!!.text = BigDecimal(sumArray[index]/determinant)
                            .setScale(2,RoundingMode.HALF_DOWN).toString()

                    }
                    else if(sumArray[index].toInt() == 0){
                         textViews[i][j]!!.text = BigDecimal(sumArray[index]/determinant)
                            .setScale(2,RoundingMode.HALF_DOWN).toString()

                    }
                    else{
                        textViews[i][j]!!.text = checker.asFraction(sumArray[index].toInt(), determinant.toInt())
                    }
                    gridLayoutForResult.addView(textViews[i][j])
                    index++
                    }
                }
                linearInverseResult.addView(gridLayoutForResult)
            }

    }
    fun calculate(){

        val calculate = Determinant()

        for(i in 0 until numberOfColumns){
            calculate.counter = 1
            determinant += calculate.determinant(i, 0,0,array,numberOfColumns )
        }

        for(i in 0 until numberOfRows){
            for(j in 0 until numberOfColumns){
                calculate.counter = 0
                calculate.neededForColumns = 0
                calculate.neededForRows = 0
                val answer = calculate.determinant(i, j, 0,array, numberOfRows)
                sumArray.add(answer)
                parentArray.add(DisplayAlgebrals(answer.toString()))
            }


        }
        adapter = Display(parentArray)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

}