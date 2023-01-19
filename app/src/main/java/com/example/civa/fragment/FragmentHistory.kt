package com.example.civa.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.*
import com.example.civa.R
import com.google.firebase.database.*
import dataclasses.HistoryVector
import recyclerviews.RecyclerViewVectorHistory


class FragmentHistory: Fragment(R.layout.fragment_history) {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewHistory
    private lateinit var database:DatabaseReference
    private var grids = mutableListOf<History>()
    private var operation = ""
    private var toPassArraylist :Array<String> = arrayOf()
    private var checkForSecondMatrixArray:GridLayout? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        database = FirebaseDatabase.getInstance().getReference("Users")

        recyclerView = view.findViewById(R.id.recyclerHistory)




        var counter = 0
        val builder = BuilderTool()
        val user = builder.userUID


        database.child(user!!).get().addOnSuccessListener {
            counter = it.child("matrix").child("0").value.toString().toInt()

            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Users").child(user).child("matrix")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in 1..counter) {
                        val key = i.toString()

                        val resultSet = dataSnapshot.child(key).getValue(String::class.java)



                        var resultArray: List<String> = resultSet!!.split(";")



                        val destinationIfClicked = resultArray.last()


                        resultArray = resultArray.dropLast(1)


                        var twoMatrixSituation = false

                        if (resultArray.last() in "+x-<>") {
                            operation = resultArray.last()
                            resultArray = resultArray.dropLast(1)
                            twoMatrixSituation = true
                        }

                        val column = resultArray[resultArray.size - 1].toInt()
                        val row = resultArray[resultArray.size - 2].toInt()
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
                                    setPos.setPosForText(textViews[i][j], i, j, 60)
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


                            grids.add(
                                History(
                                    checkForSecondMatrixArray,
                                    gridLayout,
                                    operation,
                                    destinationIfClicked,
                                    toPassArraylist,
                                    resultArray.toTypedArray()
                                )
                            )
                            checkForSecondMatrixArray = null
                            operation = ""
                        }
                    }
                    if (isAdded) {
                        adapter = RecyclerViewHistory(grids.reversed())
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }
        .addOnFailureListener{
            Toast.makeText(requireActivity(), "no internet", Toast.LENGTH_SHORT).show()
        }
    }
}