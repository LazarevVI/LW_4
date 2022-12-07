package com.example.lw_4_3_0

import android.util.Log
import android.widget.Toast

class Player(var playerName: String,
             var playerImg: Int,
             var isChecked: Boolean,
             var team:Int){

    @JvmName("setChecked1")
    fun setChecked(checked: Boolean) {
        Log.d("setchecked", playerName + " " + checked.toString())
        isChecked = checked
    }

    fun check():Boolean{
        Log.d("checkAdapter", playerName)
        return isChecked
    }

}