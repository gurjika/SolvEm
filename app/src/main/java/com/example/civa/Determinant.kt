package com.example.civa

class Determinant {
    var counter = 0
    var neededForColumns = 0
    var neededForRows = 0
    fun determinant(row:Int, column:Int, needed:Int, Array:Array<DoubleArray>, n:Int):Double{

            counter++
            var parent = 0.0

            val saveArray = Array((n - 1) - needed){DoubleArray((n - 1)- needed)}
            parent = Array[column][row]
            if(counter == 1){
                parent = 1.0
            }

            if((row + column) % 2 != 0){
                parent = -parent
            }

            val temporary:MutableList<Double> = mutableListOf()
            var e = 0

            for (i in 0.. (n - 1) - needed) {
                if (column == i) {
                    continue
                }
                for (m in 0..(n - 1) - needed) {
                    if(needed > 0 && row == n - needed - 1) {
                        if(row == m) {
                            continue
                        }
                    }
                    if(row == m){
                        continue
                    }
                        temporary.add(Array[i][m])
                }
            }
            for(i in 0..(n - 2) - needed){
                for (m in 0..(n - 2) - needed){
                    saveArray[i][m] = temporary[e]
                    e++
                }
            }

            val needed1 = needed + 1
            return if(temporary.size == 4) {
                parent*(saveArray[0][0]*saveArray[1][1]-saveArray[1][0]*saveArray[0][1])
            } else {
                var sum = 0.0
                for (i in 0..(n - 1) - needed1) {
                    sum += parent * (determinant(i, 0, needed1, saveArray, n))
                }
                sum
            }
    }
}