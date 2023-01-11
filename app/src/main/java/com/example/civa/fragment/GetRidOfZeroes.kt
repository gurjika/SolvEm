package com.example.civa.fragment

class GetRidOfZeroes {

    fun noZeroes(toCheck:String):String{
        return if(toCheck.contains(".0")){
            val result = toCheck.dropLast(2)
            result
        } else{
            toCheck
        }
    }
}