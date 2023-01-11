package com.example.civa.fragment

class ReduceFraction {

        private fun gcd(x: Int, y: Int): Int {


            return if(y == 0){
                x
            } else {
                gcd(y, x % y)
            }
        }

        fun asFraction(x: Int, y: Int): String {
            val gcd = gcd(x, y)
            return (x / gcd).toString() + "/" + "\n" + y / gcd
        }


}