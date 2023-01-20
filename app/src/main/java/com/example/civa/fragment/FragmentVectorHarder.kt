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
import com.example.civa.Determinant
import com.example.civa.R
import com.example.civa.ValidateEditTexts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt

class FragmentVectorHarder:Fragment(R.layout.fragment_vector_harder) {
    private lateinit var operation: String
    private var dimension = 3
    private lateinit var buttonCalculate: Button
    private lateinit var buttonSeeHow: Button
    private lateinit var buttonMore: Button
    private lateinit var buttonClear: Button
    private lateinit var esLinear: LinearLayout
    private lateinit var esLinearTwo: LinearLayout
    private lateinit var esLinearMatrix: LinearLayout
    private lateinit var result: TextView
    private var resultStringOne = ""
    private var resultStringTwo = ""
    private var resultStringThree = ""
    private var comeFromHistory = false
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCalculate = view.findViewById(R.id.buttonCalculateVectorHarder)
        buttonSeeHow = view.findViewById(R.id.buttonSeeHowVectorHarder)
        buttonClear = view.findViewById(R.id.buttonClearVectorHarder)
        database = FirebaseDatabase.getInstance().getReference("Users")
        sharedPreferences = this.requireActivity().getSharedPreferences(
            "Vectors",
            Context.MODE_PRIVATE
        )

        esLinear = view.findViewById(R.id.es_linearVectorHarder)
        esLinearTwo = view.findViewById(R.id.es_linearVectorHarderTwo)
        esLinearMatrix = view.findViewById(R.id.es_linearVectorHarderMatrix)

        comeFromHistory = FragmentVectorHarderArgs.fromBundle(requireArguments()).comeFromHistory

        result = view.findViewById(R.id.textViewVectorHarderResult)

        operation = FragmentVectorHarderArgs.fromBundle(requireArguments()).operation

        val arrayForMixed = Array(3) { DoubleArray(3) }
        val checker = GetRidOfZeroes()
        val reduce = ReduceFraction()
        val editTextsOne = arrayOfNulls<EditText>(3)
        val editTextsTwo = arrayOfNulls<EditText>(3)
        val editTextsThree = arrayOfNulls<EditText>(3)
        val checkerEditTexts = ValidateEditTexts()
        val builder = BuilderTool()
        builder.buildLinear(requireActivity(), editTextsOne, esLinear, 3)
        builder.buildLinear(requireActivity(), editTextsTwo, esLinearTwo, 3)



        if (operation == "MIXED") {
            builder.buildLinear(requireActivity(), editTextsThree, esLinearMatrix, 3)
            if (comeFromHistory) {
                val vectorThird =
                    FragmentVectorHarderArgs.fromBundle(requireArguments()).numbersThird
                for (i in 0 until dimension) {
                    editTextsThree[i]!!.append(vectorThird!![i])
                }
            }
        }
        if (comeFromHistory) {
            val vectorFirst =
                FragmentVectorHarderArgs.fromBundle(requireArguments()).numbers
            val vectorSecond = FragmentVectorHarderArgs.fromBundle(requireArguments()).numbersSecond
            for (i in 0 until dimension) {
                editTextsOne[i]!!.append(vectorFirst!![i])
                editTextsTwo[i]!!.append(vectorSecond!![i])
            }
        }


        buttonClear.setOnClickListener {
            for (i in 0 until 3) {
                editTextsOne[i]!!.text = null
                editTextsTwo[i]!!.text = null
                if (operation == "MIXED") {
                    editTextsThree[i]!!.text = null
                }
            }
            buttonCalculate.isEnabled = true
            result.text = null
        }

        buttonCalculate.setOnClickListener {
            if (operation == "CROSS") {
                if (checkerEditTexts.validateEmVectors(editTextsOne, 3) &&
                    checkerEditTexts.validateEmVectors(editTextsTwo, 3)
                ) {

                    val resultArray = getCross(editTextsOne, editTextsTwo)
                    result.text = "(${checker.noZeroes(resultArray[0].toString())}" +
                            ";${checker.noZeroes(resultArray[1].toString())};" +
                            "${checker.noZeroes(resultArray[2].toString())})"
                    for (i in 0 until 3) {
                        resultStringOne += checker.noZeroes(
                            editTextsOne[i]!!.text.toString().toDouble()
                                .toString()
                        ) + ";"
                        resultStringTwo += checker.noZeroes(
                            editTextsTwo[i]!!.text.toString().toDouble()
                                .toString()
                        ) + ";"
                    }
                    resultStringOne =
                        resultStringOne + "x" + ";" + dimension.toString() + ";To harder"
                    resultStringTwo = "$resultStringTwo$dimension;To harder"

                    builder.uploadVector(
                        requireActivity(),
                        database,
                        resultStringOne,
                        resultStringTwo,
                        null
                    )
                } else {
                    return@setOnClickListener
                }
                buttonCalculate.isEnabled = false
            } else if (operation == "SIN") {
                if (checkerEditTexts.validateEmVectors(editTextsOne, 3) &&
                    checkerEditTexts.validateEmVectors(editTextsTwo, 3)
                ) {

                    for (i in 0 until 3) {
                        resultStringOne += checker.noZeroes(
                            editTextsOne[i]!!.text.toString().toDouble()
                                .toString()
                        ) + ";"
                        resultStringTwo += checker.noZeroes(
                            editTextsTwo[i]!!.text.toString().toDouble()
                                .toString()
                        ) + ";"
                    }

                    resultStringOne =
                        resultStringOne + "SIN" + ";" + dimension.toString() + ";To harder"
                    resultStringTwo = "$resultStringTwo$dimension;To harder"


                    val resultArray = getCross(editTextsOne, editTextsTwo)
                    var denominator =
                        (findMagnitude(editTextsOne) * findMagnitude(editTextsTwo)).toString()

                    var numerator = (resultArray[0].pow(2) + resultArray[1].pow(2) +
                            resultArray[2].pow(2)).toString()

                    var firstOkay = false
                    var secondOkay = false

                    if (!numerator.endsWith(".0")) {
                        numerator = BigDecimal(numerator.toDouble())
                            .setScale(2, RoundingMode.HALF_DOWN).toString()
                    }
                    if (!denominator.endsWith(".0")) {
                        denominator = BigDecimal(denominator.toDouble())
                            .setScale(2, RoundingMode.HALF_DOWN).toString()
                    }

                    if (!sqrt(numerator.toDouble()).toString().endsWith(".0")) {
                        numerator = "√${checker.noZeroes(numerator)}"
                    } else {
                        numerator = sqrt(numerator.toDouble()).toString().dropLast(2)
                        firstOkay = true
                    }


                    if (!sqrt(denominator.toDouble()).toString().endsWith(".0")) {
                        denominator = "√${checker.noZeroes(denominator)}"
                    } else {
                        secondOkay = true
                        denominator = sqrt(denominator.toDouble()).toString().dropLast(2)
                    }


                    if (firstOkay && secondOkay) {
                        result.text = reduce.asFraction(
                            numerator.toDouble().toInt(),
                            denominator.toDouble().toInt()
                        )
                    } else {
                        result.text = "$numerator/\n$denominator"
                    }
                    builder.uploadVector(
                        requireActivity(),
                        database,
                        resultStringOne,
                        resultStringTwo,
                        null
                    )
                } else {
                    return@setOnClickListener
                }
                buttonCalculate.isEnabled = false
            } else {
                if (checkerEditTexts.validateEmVectors(editTextsOne, 3) &&
                    checkerEditTexts.validateEmVectors(editTextsTwo, 3)
                    && checkerEditTexts.validateEmVectors(editTextsThree, 3)
                ) {
                    for (i in 0 until 3) {

                        resultStringOne += checker.noZeroes(
                            editTextsOne[i]!!.text.toString().toDouble()
                                .toString()
                        ) + ";"
                        resultStringTwo += checker.noZeroes(
                            editTextsTwo[i]!!.text.toString().toDouble()
                                .toString()
                        ) + ";"
                        resultStringThree += checker.noZeroes(
                            editTextsThree[i]!!.text.toString()
                                .toDouble().toString()
                        ) + ";"
                    }
                    resultStringOne = "$resultStringOne,;$dimension;To Harder"
                    resultStringTwo = "$resultStringTwo,;$dimension;To harder"
                    resultStringThree = "$resultStringThree$dimension;To Harder"

                    for (i in 0 until 3) {
                        arrayForMixed[i][0] = editTextsOne[i]!!.text.toString().toDouble()
                        arrayForMixed[i][1] = editTextsTwo[i]!!.text.toString().toDouble()
                        arrayForMixed[i][2] = editTextsThree[i]!!.text.toString().toDouble()
                    }
                    val findDeterminant = Determinant()
                    var determinant = 0.0
                    for (i in 0 until 3) {
                        findDeterminant.counter = 1
                        determinant += findDeterminant.determinant(i, 0, 0, arrayForMixed, 3)
                    }
                    result.text = checker.noZeroes(determinant.toString())

                    builder.uploadVector(
                        requireActivity(),
                        database,
                        resultStringOne,
                        resultStringTwo,
                        resultStringThree
                    )

                } else {
                    return@setOnClickListener
                }
                buttonCalculate.isEnabled = false
            }
        }
    }


    private fun getCross(
        editTextsOne: Array<EditText?>,
        editTextsTwo: Array<EditText?>
    ): Array<Double> {
        val firstNumbers = mutableListOf<Double>()
        val secondNumbers = mutableListOf<Double>()


        for (i in 0 until 3) {
            firstNumbers.add(editTextsOne[i]!!.text.toString().toDouble())
            secondNumbers.add(editTextsTwo[i]!!.text.toString().toDouble())
        }

        var firstNumber = firstNumbers[1] * secondNumbers[2] - firstNumbers[2] * secondNumbers[1]
        var secondNumber = firstNumbers[0] * secondNumbers[2] - firstNumbers[2] * secondNumbers[0]
        var thirdNumber = firstNumbers[0] * secondNumbers[1] - firstNumbers[1] * secondNumbers[0]

        if (!firstNumber.toString().endsWith(".0")) {
            firstNumber = BigDecimal(firstNumber)
                .setScale(2, RoundingMode.HALF_DOWN).toDouble()
        }
        if (!secondNumber.toString().endsWith(".0")) {
            secondNumber = BigDecimal(secondNumber)
                .setScale(2, RoundingMode.HALF_DOWN).toDouble()
        }
        if (!thirdNumber.toString().endsWith(".0")) {
            thirdNumber = BigDecimal(thirdNumber)
                .setScale(2, RoundingMode.HALF_DOWN).toDouble()
        }
        return arrayOf(firstNumber, secondNumber, thirdNumber)
    }

    private fun findMagnitude(editTexts: Array<EditText?>): Double {
        return editTexts[0]!!.text.toString().toDouble().pow(2) +
                editTexts[1]!!.text.toString().toDouble().pow(2) +
                editTexts[2]!!.text.toString().toDouble().pow(2)
    }
}