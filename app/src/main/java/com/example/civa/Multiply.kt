package com.example.civa

class Multiply {
    fun multiplyEm(first:Array<DoubleArray>, second:Array<DoubleArray>):Array<DoubleArray>{
        val resultArray = Array(first.size){DoubleArray(second[0].size)}
        var sum = 0.0

        for(i in 0 until first.size) {
            for (j in 0 until second[0].size) {
                for (k in 0 until second.size) {
                    resultArray[i][j] += first[i][k] * second[k][j]
                }
            }
        }
        return resultArray
    }
}