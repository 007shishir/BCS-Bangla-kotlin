package com.example.bcsbanglagrammar.control

import androidx.annotation.WorkerThread
import com.example.bcsbanglagrammar.model.MemorizeEntity

class MyRepository(private val dao: RoomDao) {
    var putID: String? = null
//    constructor(getID: String){
//        putID = getID
//    }
//    val selectQ: List<MemorizeEntity?>? = dao.select_question(putID)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addMemorizeQ(entity: MemorizeEntity) {
        dao.addMemorizeQ(entity)
    }
}