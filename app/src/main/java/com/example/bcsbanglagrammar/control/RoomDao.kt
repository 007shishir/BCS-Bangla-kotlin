package com.example.bcsbanglagrammar.control

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bcsbanglagrammar.model.MemorizeEntity

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMemorizeQ(addingQuestion: MemorizeEntity)

    @Query("SELECT ID, Question, Ans, Explanation, CardsLevel, QuesLevel, TotalQuestion, CountTry, TriedBefore FROM memorizeTable WHERE ID=:id LIMIT 1")
    fun select_question(id: String?): List<MemorizeEntity?>?

    @Query("SELECT COUNT(QuesLevel) FROM memorizeTable WHERE QuesLevel<2 AND ID LIKE :id")
    fun countPrimaryQuestion(id: String?): Int

    @Query("SELECT COUNT(QuesLevel) FROM memorizeTable WHERE QuesLevel IN(2,3) AND ID LIKE :id")
    fun countLearning(id: String?): Int

    @Query("SELECT COUNT(QuesLevel) FROM memorizeTable WHERE QuesLevel IN(4,6) AND ID LIKE :id")
    fun countMaster(id: String?): Int

    @Query("DELETE FROM memorizeTable")
    suspend fun deleteAllMemorizeQ()
}