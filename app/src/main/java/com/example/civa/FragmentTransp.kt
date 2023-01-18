package com.example.civa

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.civa.fragment.BuilderTool
import com.example.civa.fragment.FragmentInverse
import com.example.civa.fragment.FragmentInverseArgs
import com.google.firebase.database.*

class FragmentTransp: Fragment(R.layout.fragment_transp) {

    private lateinit var linearTranspResult: LinearLayout
    private lateinit var buttonCalculate: Button
    private lateinit var clear: Button
    private lateinit var esLinear: LinearLayout

    private var resultString = ""
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

        numberOfColumns = dimension
        numberOfRows = dimension
        comeFromHistory = FragmentTranspArgs.fromBundle(requireArguments()).comeFromHistory
        val toAssembleString = FragmentTranspArgs.fromBundle(requireArguments()).dimension
        numberOfRows = toAssembleString[0].toString().toInt()
        numberOfColumns = toAssembleString[1].toString().toInt()
        buttonCalculate = view.findViewById(R.id.buttonCalculateTrasnp)
        esLinear = view.findViewById(R.id.es_linearTranp)
        seeHowButton = view.findViewById(R.id.seeHowButtonTransp)
        clear = view.findViewById(R.id.buttonClearTransp)
        linearTranspResult = view.findViewById(R.id.linearResultTrasnp)
        val editTexts = Array(numberOfRows) { arrayOfNulls<EditText>(numberOfColumns) }
        val textViews = Array(numberOfColumns) { arrayOfNulls<TextView>(numberOfRows) }
        val checkEditText = ValidateEditTexts()
        array = Array(numberOfRows) { DoubleArray(numberOfColumns) }
        comeFromHistory = FragmentInverseArgs.fromBundle(requireArguments()).comeFromHistory
        val builder = BuilderTool()
        val gridLayout = builder.buildGrid(
            requireActivity(),
            editTexts,
            numberOfRows,
            numberOfColumns
        )

        esLinear.addView(gridLayout)
        if(comeFromHistory){
            val receivedArray = FragmentTranspArgs.fromBundle(requireArguments()).matrix
            var index = 0
            for(i in 0 until numberOfRows){
                for(j in 0 until numberOfColumns){
                    editTexts[i][j]!!.append(receivedArray!![index])
                    index++
                }
            }
        }


        buttonCalculate.setOnClickListener {
            if(checkEditText.validateEm(editTexts, numberOfRows, numberOfColumns)) {
                for (i in 0 until numberOfRows) {
                    for (j in 0 until numberOfColumns) {
                        array[i][j] = editTexts[i][j]!!.text.toString().toDouble()
                        resultString = resultString + array[i][j].toString() + ";"
                    }
                }
            }
            else{
                return@setOnClickListener
            }
            linearTranspResult.addView(
                makeActualTextViewGridLayout(
                    numberOfRows,
                    array, numberOfColumns, textViews
                )
            )


            resultString = "$resultString$numberOfRows;$numberOfColumns;TRANSP"
            database = FirebaseDatabase.getInstance().getReference("Users")
            sharedPreferences = this.requireActivity().getSharedPreferences(
                "MY_PREFS",
                Context.MODE_PRIVATE
            )

            if (!comeFromHistory) {
                builder.uploadMatrix(
                    requireActivity(),
                    database,
                    sharedPreferences,
                    resultString,
                    null
                )
            }
        }

    }

    private fun makeActualTextViewGridLayout(numberOfRows:Int,
                                     ArrayOfText:Array<DoubleArray>,
                                     numberOfColumns:Int,
                                     textViews:Array<Array<TextView?>>):GridLayout{
        val gridLayout = GridLayout(requireActivity())
        val setPos = MakeGridLayout()
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfColumns) {
                textViews[j][i] = TextView(requireActivity())
                setPos.setPosForText(textViews[j][i], j, i, 100)
                textViews[j][i]!!.text = ArrayOfText[i][j].toString()
                gridLayout.addView(textViews[j][i])
            }
        }
        return gridLayout
    }
}