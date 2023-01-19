package com.example.civa.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.*
import com.example.civa.R
import com.google.firebase.database.*
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
    private var toSendArray: MutableList<DisplayAlgebrals> = mutableListOf()
    private var algebralArray: MutableList<Double> = mutableListOf()
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
        seeHowButton.isEnabled = false
        val setPos = MakeGridLayout()
        val builder = BuilderTool()
        array = Array(n) { DoubleArray(n) }
        comeFromHistory = FragmentInverseArgs.fromBundle(requireArguments()).comeFromHistory
        val gridLayout = builder.buildGrid(requireActivity(),editTexts, numberOfRows, numberOfColumns)

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


        val checkEditText = ValidateEditTexts()
        clear.setOnClickListener {
            for(i in 0 until numberOfRows){
                for(j in 0 until numberOfColumns){
                    editTexts[i][j]!!.text = null
                    array[i][j] = 0.0
                }
            }
            linearInverseResult.visibility = View.INVISIBLE
            linear.visibility = View.VISIBLE
            resultString = ""
            algebralArray.clear()
            toSendArray.clear()
            recyclerView.visibility = View.GONE
            adapter = Display(toSendArray)
            adapter.notifyDataSetChanged()
            seeHowButton.isEnabled = false
            buttonCalculate.isEnabled = true
        }

        seeHowButton.setOnClickListener {

            seeHowButton.isEnabled = false
            buttonCalculate.isEnabled = false
            recyclerView.visibility = View.VISIBLE
            adapter = Display(toSendArray)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        }
        val check = GetRidOfZeroes()
        buttonCalculate.setOnClickListener {

            if(!builder.checkInternet(requireActivity())){
                Toast.makeText(requireActivity(), "inte ar ari", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val calculate = Determinant()
            if (checkEditText.validateEm(editTexts, n, n)) {
                for (i in 0 until n) {
                    for (j in 0 until n) {
                        array[i][j] = editTexts[i][j]?.text.toString().toDouble()
                        resultString = resultString + check.noZeroes(array[i][j].toString()) + ";"
                    }
                }
            }
            else{
                return@setOnClickListener
            }
            if(array.size == 2){
                determinant = array[0][0] * array[1][1] - array[0][1] * array[1][0]
            }
            else {
                for (i in 0 until numberOfColumns) {
                    calculate.counter = 1
                    determinant += calculate.determinant(i, 0, 0, array, numberOfColumns)
                }
            }
            if(determinant == 0.0){
                Toast.makeText(requireActivity(), "დეტერმინანტი ნულის ტოლია", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            buttonCalculate.isEnabled = false

            algebralArray.clear()
            toSendArray.clear()
            seeHowButton.isEnabled = true
            linear.visibility = View.INVISIBLE

            resultString = "$resultString$n;$n;INVERSE"

            database = FirebaseDatabase.getInstance().getReference("Users")
            if(array.size == 2) {
                algebralArray.add(determinant * array[1][1])
                algebralArray.add(-determinant * array[0][1])
                algebralArray.add(-determinant * array[1][0])
                algebralArray.add(determinant * array[0][0])
                seeHowButton.isEnabled = false
            }
            else{
                calculate()
            }

            if(!comeFromHistory) {
                builder.uploadMatrix(requireActivity(), database, resultString, null)
            }
            comeFromHistory = true
            var index = 0
            val checker = ReduceFraction()

            val gridLayoutForResult = GridLayout(requireActivity())

            gridLayoutForResult.rowCount = numberOfRows
            gridLayoutForResult.columnCount = numberOfColumns

            for (i in 0 until numberOfRows) {
                for (j in 0 until numberOfColumns) {

                    textViews[i][j] = TextView(requireActivity())
                    setPos.setPosForText(textViews[i][j], i, j, 150)

                    if(!determinant.toString().endsWith(".0") ||
                            !algebralArray[index].toString().endsWith(".0")){
                        determinant = BigDecimal(determinant)
                            .setScale(2,RoundingMode.HALF_DOWN).toDouble()
                        algebralArray[index] = BigDecimal(algebralArray[index])
                            .setScale(2, RoundingMode.HALF_DOWN).toDouble()
                        textViews[i][j]!!.text =  "${algebralArray[index]}/\n$determinant"
                    }

                    else{
                        textViews[i][j]!!.text = checker.asFraction(
                            algebralArray[index].toInt(),determinant.toInt())
                        if(textViews[i][j]!!.text.toString().endsWith("/1")){
                            textViews[i][j]!!.text = textViews[i][j]!!.text.dropLast(2)
                        }
                    }
                    gridLayoutForResult.addView(textViews[i][j])
                    index++
                    }
                }
                linearInverseResult.removeAllViews()
                linearInverseResult.addView(gridLayoutForResult)
                linearInverseResult.visibility = View.VISIBLE
            }

    }
    fun calculate(){

        val calculate = Determinant()

        val check = GetRidOfZeroes()

        for(i in 0 until numberOfRows){
            for(j in 0 until numberOfColumns){
                calculate.counter = 0
                calculate.neededForColumns = 0
                calculate.neededForRows = 0
                val answer = calculate.determinant(i, j, 0,array, numberOfRows)
                algebralArray.add(answer)
                toSendArray.add(DisplayAlgebrals(check.noZeroes(answer.toString()),
                    (i + 1).toString()+(j + 1).toString(), "A") )
            }
        }
        toSendArray.add(DisplayAlgebrals(check.noZeroes(determinant.toString()),"", "D"))
    }
}