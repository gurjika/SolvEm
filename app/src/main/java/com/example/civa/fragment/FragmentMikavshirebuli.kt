package com.example.civa.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.*
import java.math.BigDecimal
import java.math.RoundingMode

class FragmentMikavshirebuli:Fragment(R.layout.fragment_mikavshirebuli) {
    private lateinit var result: TextView
    private lateinit var buttonCalculate: Button
    private lateinit var clear: Button
    private lateinit var linear: LinearLayout
    private var counter = 0
    private var n = 4
    private var determinant = 0.0
    private lateinit var linearInverseResult: LinearLayout
    private var oldParent = 0.0
    private var oldNewParent = 0.0
    private lateinit var adapter: Display
    private lateinit var resultLinearLayout: LinearLayout
    private var parentArray: MutableList<DisplayAlgebrals> = mutableListOf()
    private var sumArray: MutableList<Double> = mutableListOf()
    private var numberOfColumns = 4
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
        buttonCalculate = view.findViewById(R.id.buttonCalculateMikavshirebuli)
        linear = view.findViewById(R.id.es_linearMikavshirebuli)
        recyclerView = view.findViewById(R.id.recyclerViewDeterminantMikavshirebuli)
        seeHowButton = view.findViewById(R.id.seeHowButtonMikavshirebuli)
        clear = view.findViewById(R.id.buttonClearMikavshirebuli)
        linearInverseResult = view.findViewById(R.id.linearResultMikavshirebuli)
        val editTexts = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val textViews = Array(numberOfRows) { arrayOfNulls<TextView>(numberOfColumns) }
        val gridLayout = GridLayout(activity)
        val setPos = MakeGridLayout()
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

        val gridLayoutForResult = GridLayout(requireActivity())

        gridLayoutForResult.rowCount = numberOfRows
        gridLayoutForResult.columnCount = numberOfColumns

        val checkEditText = ValidateEditTexts()


        buttonCalculate.setOnClickListener {
            for (i in 0 until n) {
                for (j in 0 until n) {
                    if (checkEditText.validateEm(editTexts, n, n)) {
                        array[i][j] = editTexts[i][j]?.text.toString().toDouble()
                    } else {
                        return@setOnClickListener
                    }
                }
            }


            calculate()

            var index = 0
            val checker = ReduceFraction()
            val check = GetRidOfZeroes()

            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {
                    textViews[i][j] = TextView(requireActivity())
                    setPos.setPosForText(textViews[i][j], i, j, 100)
                    gridLayout.addView(textViews[i][j])
                }
            }
            linearInverseResult.addView(gridLayoutForResult)
        }

    }
    private fun calculate(){

        val calculate = Determinant()
        calculate.counter = 0
        for(i in 0..3){
            for(j in 0..3){
                calculate.neededForColumns = 0
                calculate.neededForRows = 0
                val answer = calculate.determinant(i, j, 0,array, 4)
                sumArray.add(answer)
                parentArray.add(DisplayAlgebrals(answer.toString()))
            }
        }
        adapter = Display(parentArray)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }
}