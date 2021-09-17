package com.goodee.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[ToDoList::class], version = 2, exportSchema = false)
abstract class ToDoListDatabase: RoomDatabase(){
    abstract val toDoListDao: ToDoListDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoListDatabase? = null

        fun getInstance(context: Context): ToDoListDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoListDatabase::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}