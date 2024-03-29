package com.example.civa.fragment

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

class FragmentDeterminant:Fragment(R.layout.fragment_determinant) {
    private lateinit var result: TextView
    private lateinit var buttonCalculateDeterminant: Button
    private lateinit var buttonClear: Button
    private lateinit var linear: LinearLayout
    private var counter = 0
    private var n = 0
    private var comeFromHistory = false
    private var oldParent = 0.0
    private var oldNewParent = 0.0
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: TableAdapter

    private var parentArray:MutableList<String> = mutableListOf()
    private var sumArray:MutableList<String> = mutableListOf()
    private var numberOfColumns = 5
    private lateinit var resultString:String
    private lateinit var recyclerView: RecyclerView
    private lateinit var seeHowButton: Button
    private var tablelist = mutableListOf<Table>()
    private var toAddInTableList = mutableListOf<GridLayout>()
    private var array = Array(5) { DoubleArray(5) }
    private var saveArray = Array(5) { DoubleArray(5) }
    private var numberOfRows = 5
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewDeterminant)
        seeHowButton = view.findViewById(R.id.seeHowButton)
        buttonClear = view.findViewById(R.id.buttonClear)
        result = view.findViewById(R.id.textView)
        buttonCalculateDeterminant = view.findViewById(R.id.buttonCalculateDeterminant)
        linear = view.findViewById(R.id.es_linear)

        val checkEditText = ValidateEditTexts()
        val checker = GetRidOfZeroes()
        n = FragmentDeterminantArgs.fromBundle(requireArguments()).dimension

        comeFromHistory = FragmentDeterminantArgs.fromBundle(requireArguments()).comeFromHistory
        resultString = ""
        seeHowButton.isEnabled = false
        numberOfRows = n
        numberOfColumns = n
        array = Array(n) { DoubleArray(n) }
        saveArray = Array(n) { DoubleArray(n) }


        val editTexts = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val builder = BuilderTool()

        val gridLayout = builder.buildGrid(
            requireActivity(),
            editTexts,
            numberOfRows,
            numberOfColumns)
        linear.addView(gridLayout)



        if(comeFromHistory){
            var index = 0
            val receivedArray = FragmentDeterminantArgs.fromBundle(requireArguments()).matrix
            for(i in 0 until n){
                for(j in 0 until n){
                    editTexts[i][j]!!.append(receivedArray!![index])
                    index++
                }
            }
        }

        seeHowButton.setOnClickListener {
            turnOnRecycler()
            seeHowButton.isEnabled = false
            buttonCalculateDeterminant.isEnabled = false
            adapter = TableAdapter(tablelist)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.visibility = View.VISIBLE
            linear.visibility = View.INVISIBLE
            result.visibility = View.INVISIBLE
        }
        buttonCalculateDeterminant.setOnClickListener {
            if(!builder.checkInternet(requireActivity())){
                Toast.makeText(requireActivity(), "inte ar ari", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (checkEditText.validateEm(editTexts, n, n)) {
                for (i in 0 until n) {
                    for (j in 0 until n) {
                        array[i][j] = editTexts[i][j]?.text.toString().toDouble()
                        resultString = resultString + checker.noZeroes(array[i][j].toString()) + ";"
                    }
                }
            }
            else{
                return@setOnClickListener
            }
            sumArray.add("")
            parentArray.add("")
            buttonCalculateDeterminant.isEnabled = false
            seeHowButton.isEnabled = true
            display(array)
            aba()



            resultString = "$resultString$n;$n;DETERMINANT"

            database = FirebaseDatabase.getInstance().getReference("Users")


            if (!comeFromHistory) {
                builder.uploadMatrix(
                    requireActivity(),
                    database,
                    resultString,
                    null)
            }
        }
        buttonClear.setOnClickListener {
            for (i in 0 until n) {
                for (j in 0 until n) {
                    editTexts[i][j]?.text = null
                    array[i][j] = 0.0
                }
            }
            tablelist.clear()
            toAddInTableList.clear()
            parentArray.clear()
            sumArray.clear()
            adapter = TableAdapter(tablelist)
            adapter.notifyDataSetChanged()
            recyclerView.visibility = View.INVISIBLE
            result.visibility = View.VISIBLE
            linear.visibility = View.VISIBLE
            val setItToZero = TableAdapter(tablelist)
            setItToZero.e = 0
            buttonCalculateDeterminant.isEnabled = true
            seeHowButton.isEnabled = false
            counter = 0
            result.text = "Determinant: "

        }
    }





    private fun aba() {
        val checker = GetRidOfZeroes()

        var sum = 0.0
        if(n == 2) {
            val result2x2 = array[0][0] * array[1][1] - array[1][0] * array[0][1]
            result.text = "Determinant:${checker.noZeroes(result2x2.toString())}"
        }
        else {
            for (i in 0 until n) {
                sum += determinant(i, 0, 0, saveArray)
            }
            result.text = "Determinant:${checker.noZeroes(sum.toString())}"
        }
    }

    private fun determinant(column:Int, row:Int, needed:Int, Array:Array<DoubleArray>):Double{
        counter ++
        var parent = 0.0

        val saveArray = Array((n - 1) - needed){DoubleArray((n - 1)- needed)}
        if(needed == 0){
            parent = array[row][column]
        }
        if(needed > 0){
            parent = Array[row][column]
        }
        if((column + row) % 2 != 0){
            parent = -parent
        }

        when (needed) {
            0 -> {
                oldParent = parent
                parentArray.add(parent.toString())
            }
            1 -> {
                parentArray.add((parent * oldParent).toString())
                oldNewParent = parent * oldParent
            }
            2 -> {
                parentArray.add((parent * oldNewParent).toString())
            }
        }

        val temporary:MutableList<Double> = mutableListOf()
        var e = 0

        for (i in 0.. (n - 1) - needed) {
            if (row == i) {
                continue
            }
            for (m in 0..(n - 1) - needed) {

                if(column == m){
                    continue
                }
                if(needed == 0){
                    temporary.add(array[i][m])
                }
                else{
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

        display(saveArray)
        sums(saveArray)


        val neededOne = needed + 1
        return if(temporary.size == 4) {
            parent*(saveArray[0][0]*saveArray[1][1]-saveArray[1][0]*saveArray[0][1])
        } else {
            var sum = 0.0
            for (i in 0..(n - 1) - neededOne) {
                sum += parent * (determinant(i, row, neededOne, saveArray))
            }
            sum
        }
    }



    private fun display(Array: Array<DoubleArray>){
        val dimension = Array.size
        val tableLayout = GridLayout(requireActivity())
        tableLayout.id = Math.random().toInt()
        tableLayout.rowCount = dimension
        tableLayout.columnCount = dimension

        val textViews = Array(dimension) { arrayOfNulls<TextView>(dimension)}
        val setPos = MakeGridLayout()
        for (i in 0 until dimension) {
            for (j in 0 until dimension) {
                textViews[i][j] = TextView(requireActivity())
                setPos.setPosForText(textViews[i][j], i, j, 100)
                tableLayout.addView(textViews[i][j])
                if(Array[i][j].toString().contains(".0")){
                    Array[i][j].toString().dropLast(2)
                }
                textViews[i][j]!!.text = Array[i][j].toInt().toString()
            }
        }
        toAddInTableList.add(tableLayout)

    }
    private fun turnOnRecycler(){
        val checker = GetRidOfZeroes()
        for(j in 0..counter - sumArray.size){
            sumArray.add("")
        }
        for(i in 0 .. counter){
            tablelist.add(Table(toAddInTableList[i], checker.noZeroes(parentArray[i]),
                checker.noZeroes(sumArray[i])))
        }
    }
    private fun sums(Array: Array<DoubleArray>){
        if(Array.size == 2){
            sumArray.add((parentArray[counter].toDouble() * twoByTwo(Array)).toString())
        }
        else {
            sumArray.add("")
        }
    }
    private fun twoByTwo(Array: Array<DoubleArray>):Double{
        return if(Array.size == 2){
            Array[0][0] * Array[1][1] - Array[1][0] * Array[0][1]
        } else {
            0.0
        }
    }
}


