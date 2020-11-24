package com.example.bcsbanglagrammar

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bcsbanglagrammar.control.MyRepository
import com.example.bcsbanglagrammar.model.MemorizeEntity


class Memorize_ViewMode2(private val repository: MyRepository): ViewModel() {

    suspend fun addMemorizeQ(entity: MemorizeEntity){
        repository.addMemorizeQ(entity)
    }
}
