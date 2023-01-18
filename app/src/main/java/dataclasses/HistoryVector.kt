package dataclasses

import android.widget.GridLayout


data class HistoryVector(
    val dimension:String,
    val toSend:Array<String>,
    val toSendSecond:Array<String>,
    val toSendThird:Array<String>?,
    val vectorOne:GridLayout,
    val vectorTwo:GridLayout,
    val vectorThree:GridLayout?,
    val operation:String,
    val destination:String,
)
