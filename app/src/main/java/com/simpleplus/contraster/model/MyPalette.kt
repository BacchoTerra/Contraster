package com.simpleplus.contraster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palette_table")
data class MyPalette (val name:String ,val ratioLabel:String, val backGroundColor:Int, val foregroundColor:Int, @PrimaryKey(autoGenerate = true) val id: Int =0){
}