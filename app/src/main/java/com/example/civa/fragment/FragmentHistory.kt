package com.example.civa.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FragmentHistory: Fragment(R.layout.fragment_history) {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewHistory
    private lateinit var database:DatabaseReference
    private var grids = mutableListOf<History>()
    private var toPassArraylist :Array<String> = arrayOf()
    private var checkForSecondMatrixArray:GridLayout? = null
    @SuppressLint("MutatingSharedPrefs")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        database = FirebaseDatabase.getInstance().getReference("Users")
        sharedPreferences = this.requireActivity().getSharedPreferences("MY_PREFS", MODE_PRIVATE)
        recyclerView = view.findViewById(R.id.recyclerHistory)

        //database.child("email").get().addOnSuccessListener{
            //sharedPreferences.edit()
//                .putString("1", it.child("1").value.toString())
//                .apply()
//            es_linearHistory = view.findViewById(R.id.es_linearHistory)
//            val scatteredArray = mutableListOf<String>()
//
//            sharedPreferences = this.requireActivity().getSharedPreferences("MY_PREFS", MODE_PRIVATE)
//            var toAssemble = sharedPreferences.getString("1", "")
//            var sum = ""
//            for(i in 0 until toAssemble!!.length - 2) {
//                if(toAssemble[i].toString() != ";"){
//                    sum += toAssemble[i]
//                }
//                else{
//                    resultArray.add(sum)
//                    sum = ""
//                    continue
//                }
//            }
//
//            val row = toAssemble.last().toString().toInt()
//            toAssemble = toAssemble.dropLast(1)
//            val column = toAssemble.last().toString().toInt()
//
//
//            var index = 0
//            var setPos = MakeGridLayout()
//            val textViews = Array(row) { arrayOfNulls<TextView>(column) }
//            val gridLayout = GridLayout(requireActivity())
//            gridLayout.rowCount = row
//            gridLayout.columnCount = column
//            for(i in 0 until row){
//                for(j in 0 until column){
//                    textViews[i][j] = TextView(requireActivity())
//                    setPos.setPosForText(textViews[i][j], i, j, 50)
//                    textViews[i][j]!!.text = resultArray[index]
//
//                    index++
//                    gridLayout.addView(textViews[i][j])
//                }
//            }
//            es_linearHistory.addView(gridLayout)
//        }


        var counter = 0


        database.child("email").get().addOnSuccessListener {
            counter = it.child("0").value.toString().toInt()

            for (k in 1..counter) {
                val resultSet = sharedPreferences.getString(k.toString(), null)
                var resultArray: List<String> = resultSet!!.split(";")
                var destinationIfClicked = resultArray.last()
                resultArray = resultArray.dropLast(1)
                var twoMatrixSituation = false
                var operation = " "
                if (resultArray.last() in "+x-") {
                    operation = resultArray.last()
                    resultArray = resultArray.dropLast(1)
                    twoMatrixSituation = true
                }

                val row = resultArray[resultArray.size - 1].toInt()
                val column = resultArray[resultArray.size - 2].toInt()
                resultArray.dropLast(1)


                var index = 0
                val setPos = MakeGridLayout()
                val textViews = Array(row) { arrayOfNulls<TextView>(column) }
                if (isAdded) {
                    val gridLayout = GridLayout(requireActivity())
                    gridLayout.rowCount = row
                    gridLayout.columnCount = column
                    for (i in 0 until row) {
                        for (j in 0 until column) {
                            textViews[i][j] = TextView(requireActivity())
                            setPos.setPosForText(textViews[i][j], i, j, 50)

                            textViews[i][j]!!.text = resultArray[index]
                            index++
                            gridLayout.addView(textViews[i][j])

                        }
                    }
                    if (twoMatrixSituation) {
                        checkForSecondMatrixArray = gridLayout
                        toPassArraylist = resultArray.toTypedArray()
                        continue
                    }


                    grids.add(History(gridLayout, checkForSecondMatrixArray,
                        operation, destinationIfClicked, resultArray.toTypedArray(), toPassArraylist))
                    checkForSecondMatrixArray = null
                }
                adapter = RecyclerViewHistory(grids.reversed())
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            }
        }
    }
}