package com.goodee.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoListDao {
    // todolist 생성
    @Insert
    suspend fun insertToDo(toDoList: ToDoList)

    // 모든 할 일 조회
    @Query("SELECT * FROM ToDoList")
    fun getAllToDoList(): LiveData<List<ToDoList>>

    // 체크 안 된 일만 보기
    @Query("SELECT * FROM ToDoList WHERE isFinish = 0")
    fun getToDoList(): LiveData<List<ToDoList>>

    // 체크 된 일만 보기
    @Query("SELECT * FROM ToDoList WHERE isFinish = 1")
    fun getDoneList(): LiveData<List<ToDoList>>

    // 모든 일 삭제.
    @Query("DELETE FROM ToDoList")
    suspend fun deleteAllToDo()

    // 한 일 체크하기
    @Query("UPDATE ToDoList SET isFinish = 0")
    suspend fun finishToDoList()
}