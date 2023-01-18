package com.example.civa

import android.widget.GridLayout

data class History(
    val historyMatrix:GridLayout?,
    val historyMatrixTwo: GridLayout?,
    val operation:String?,
    val destination:String,
    val toSend:Array<String>,
    val toSendSecond:Array<String>
)