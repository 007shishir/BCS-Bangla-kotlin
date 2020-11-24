package com.example.bcsbanglagrammar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memorizeTable")
class MemorizeEntity (
    @PrimaryKey @ColumnInfo(name = "ID") val id: String,
    @ColumnInfo(name = "Question") val q: String,
    @ColumnInfo(name = "Ans") val a: String,
    @ColumnInfo(name = "Explanation") val ee: String,
    @ColumnInfo(name = "TotalQuestion") val totalNQ: Int,
   // @ColumnInfo(name = "TotalExplanation") val totalE: Int,
    @ColumnInfo(name = "CardsLevel") val levelCards: Int,
    @ColumnInfo(name = "QuesLevel") val levelQuestin: Int,
    @ColumnInfo(name = "CountTry") val totalTry: Int,
    @ColumnInfo(name = "TriedBefore") val triedBefore: Boolean
)