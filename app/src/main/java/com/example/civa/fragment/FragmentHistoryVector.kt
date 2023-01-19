package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.MakeGridLayout
import com.example.civa.R
import com.google.firebase.database.*
import dataclasses.HistoryBinary
import dataclasses.HistoryVector
import recyclerviews.RecyclerViewHistoryBinary
import recyclerviews.RecyclerViewVectorHistory

class FragmentHistoryVector:Fragment(R.layout.fragment_history_vector) {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewVectorHistory
    private lateinit var database: DatabaseReference
    private var grids = mutableListOf<GridLayout?>()
    private var toSendData = mutableListOf<HistoryVector>()
    private var operation = ""
    private var toSaveVectors: Array<Array<String>?> = arrayOfNulls(3)
    private var indexForVectorArray = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("Users")



        var counter = 0
        recyclerView = view.findViewById(R.id.recyclerVectorHistory)
        val builder = BuilderTool()
        val user = builder.userUID



        database.child(user!!).child("vector").get().addOnSuccessListener {
            counter = it.child("0").value.toString().toInt()
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Users").child(user!!).child("vector")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in 1..counter) {
                        val key = i.toString()
                        val resultSet = dataSnapshot.child(key).getValue(String::class.java)

                        var resultArray: List<String> = resultSet!!.split(";")

                        val destinationIfClicked = resultArray.last()
                        resultArray = resultArray.dropLast(1)

                        val dimension = resultArray.last().toInt()
                        resultArray = resultArray.dropLast(1)


                        var fewVectorSituation = false

                        if (resultArray.last() in ",x*" ||
                            resultArray.last() == "COS" || resultArray.last() == "SIN"
                        ) {
                            fewVectorSituation = true
                            operation = resultArray.last()
                            resultArray = resultArray.dropLast(1)
                        }


                        val textViews = arrayOfNulls<TextView>(dimension)
                        val setPos = MakeGridLayout()

                        if (isAdded) {
                            val gridLayout = GridLayout(requireActivity())
                            gridLayout.rowCount = 1
                            gridLayout.columnCount = dimension

                            for (j in 0 until dimension) {
                                textViews[j] = TextView(requireActivity())
                                setPos.setPosForText(textViews[j], 0, j, 50)
                                textViews[j]!!.text = resultArray[j]
                                gridLayout.addView(textViews[j])
                            }

                            grids.add(gridLayout)
                            toSaveVectors[indexForVectorArray] = resultArray.toTypedArray()
                            indexForVectorArray++

                            if (fewVectorSituation) {
                                continue
                            }

                            if (grids.size == 2) {
                                grids.add(null)
                            }

                            toSendData.add(
                                HistoryVector(
                                    dimension.toString(),
                                    toSaveVectors[0]!!,
                                    toSaveVectors[1]!!,
                                    toSaveVectors[2],
                                    grids[0]!!,
                                    grids[1]!!,
                                    grids[2],
                                    operation,
                                    destinationIfClicked
                                )
                            )

                            indexForVectorArray = 0
                            toSaveVectors[2] = null
                            grids.clear()
                        }

                    }
                    if (isAdded) {
                        adapter = RecyclerViewVectorHistory(toSendData.reversed())
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }
    }
}