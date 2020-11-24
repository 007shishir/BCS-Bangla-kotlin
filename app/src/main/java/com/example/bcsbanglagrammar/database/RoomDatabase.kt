package com.example.bcsbanglagrammar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bcsbanglagrammar.control.RoomDao
import com.example.bcsbanglagrammar.model.MemorizeEntity
import java.security.AccessControlContext

@Database(entities = arrayOf(MemorizeEntity::class), version = 1, exportSchema = false)
public abstract class MyDatabase: RoomDatabase() {
    abstract fun dao(): RoomDao
    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}