package com.example.civa.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.civa.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt


class FragmentVectorEasier:Fragment(R.layout.fragment_vector_easier) {
    private lateinit var operation: String
    private var dimension = 0
    private lateinit var buttonCalculate: Button
    private lateinit var buttonSeeHow: Button
    private lateinit var buttonMore: Button
    private lateinit var buttonClear: Button
    private lateinit var esLinear:LinearLayout
    private lateinit var esLinearTwo:LinearLayout
    private lateinit var result:TextView
    private var resultStringOne = ""
    private var resultStringTwo = ""
    private var comeFromHistory = false
    private lateinit var database:DatabaseReference
    private lateinit var sharedPreferences:SharedPreferences
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        operation = FragmentVectorEasierArgs.fromBundle(requireArguments()).operation
        dimension = FragmentVectorEasierArgs.fromBundle(requireArguments()).dimension.toInt()
        esLinear = view.findViewById(R.id.es_linearVectorEasier)
        esLinearTwo = view.findViewById(R.id.es_linearVectorEasierTwo)
        database = FirebaseDatabase.getInstance().getReference("Users")
        sharedPreferences = this.requireActivity().getSharedPreferences(
            "Vectors",
            Context.MODE_PRIVATE
        )

        buttonCalculate = view.findViewById(R.id.buttonCalculateVectorEasier)
        buttonSeeHow = view.findViewById(R.id.buttonSeeHowVectorEasier)
        buttonMore = view.findViewById(R.id.buttonMoreVectorEasier)
        buttonClear = view.findViewById(R.id.buttonClearVectorEasier)
        result = view.findViewById(R.id.textViewVectorEasierResult)
        val numbersForFirst = mutableListOf<Double>()
        val numbersForSecond = mutableListOf<Double>()
        comeFromHistory = FragmentVectorEasierArgs.fromBundle(requireArguments()).comeFromHistory
        val editTextsOne = arrayOfNulls<EditText>(dimension)
        val editTextsTwo = arrayOfNulls<EditText>(dimension)


        val builder = BuilderTool()
        builder.buildLinear(requireActivity(), editTextsOne, esLinear, dimension)

        builder.buildLinear(requireActivity(), editTextsTwo, esLinearTwo, dimension)
        val checker = GetRidOfZeroes()
        val reduce = ReduceFraction()

        if(comeFromHistory){
            val vectorFirst = FragmentVectorEasierArgs.fromBundle(requireArguments()).numbers
            val vectorSecond = FragmentVectorEasierArgs.fromBundle(requireArguments()).numbersSecond
            for(i in 0 until dimension){
                editTextsOne[i]!!.append(vectorFirst!![i])
                editTextsTwo[i]!!.append(vectorSecond!![i])
            }
        }
        buttonCalculate.setOnClickListener {
            if (operation == "COS") {
                var sum = 0.0
                var goToDenominatorOne = 0.0
                var goToDenominatorTwo = 0.0

                for(i in 0 until 3){
                    resultStringOne += editTextsOne[i]!!.text.toString().toDouble().toString() + ";"
                    resultStringTwo += editTextsTwo[i]!!.text.toString().toDouble().toString() + ";"
                }
                resultStringOne = resultStringOne + "COS" + ";" + dimension.toString() + ";To easier"
                resultStringTwo = "$resultStringTwo$dimension;To easier"

                for (i in 0 until dimension) {
                    numbersForFirst.add(editTextsOne[i]!!.text.toString().toDouble())
                    numbersForSecond.add(editTextsTwo[i]!!.text.toString().toDouble())
                    sum += numbersForFirst[i] * numbersForSecond[i]
                    goToDenominatorOne += numbersForFirst[i].pow(2)
                    goToDenominatorTwo += numbersForSecond[i].pow(2)
                }
                var denominator = (goToDenominatorOne * goToDenominatorTwo).toString()
                var firstOkay = false
                var secondOkay = false
                var numerator = sum.toString()
                if(!numerator.endsWith(".0")){
                    numerator = BigDecimal(numerator.toDouble())
                        .setScale(2, RoundingMode.HALF_DOWN).toString()
                }
                else{
                    firstOkay = true
                }
                if(!denominator.endsWith(".0")){
                    denominator = BigDecimal(denominator.toDouble())
                        .setScale(2, RoundingMode.HALF_DOWN).toString()
                }

                if(!sqrt(denominator.toDouble()).toString().endsWith(".0")){
                    denominator = "√${checker.noZeroes(denominator)}"
                }
                else{
                    secondOkay = true
                    denominator = sqrt(denominator.toDouble()).toString().dropLast(2)
                }
                if(firstOkay && secondOkay) {
                    result.text = reduce.asFraction(numerator.toDouble().toInt(), denominator.toDouble().toInt())
                }
                else{
                    result.text = "${checker.noZeroes(numerator)}/\n$denominator"
                }


                builder.uploadVector(requireActivity(),
                    database,
                    sharedPreferences,
                    resultStringOne,
                    resultStringTwo,
                    null)
            }
            else{
                for(i in 0 until 3){
                    resultStringOne += editTextsOne[i]!!.text.toString().toDouble().toString() + ";"
                    resultStringTwo += editTextsTwo[i]!!.text.toString().toDouble().toString() + ";"
                }
                resultStringOne = "$resultStringOne*;$dimension;To easier"
                resultStringTwo = "$resultStringTwo$dimension;To easier"

                val results = mutableListOf<String>()
                for (i in 0 until dimension) {
                    numbersForFirst.add(editTextsOne[i]!!.text.toString().toDouble())
                    numbersForSecond.add(editTextsTwo[i]!!.text.toString().toDouble())
                    if(!(numbersForFirst[i] * numbersForSecond[i]).toString().endsWith(".0")){
                        results.add(
                            BigDecimal(numbersForFirst[i] * numbersForSecond[i])
                                .setScale(2, RoundingMode.HALF_DOWN).toString())
                    }
                    else{
                        results
                            .add(checker.noZeroes((numbersForFirst[i] * numbersForSecond[i]).toString()))
                    }
                }
                if(dimension == 3){
                    result.text = "(${results[0]};${results[1]};${results[2]})"
                }
                else{
                    result.text = "(${results[0]};${results[1]})"
                }
                builder.uploadVector(requireActivity(),
                    database,
                    sharedPreferences,
                    resultStringOne,
                    resultStringTwo,
                    null)
            }
        }
    }
}