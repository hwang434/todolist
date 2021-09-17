package com.goodee.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.goodee.todolist.database.ToDoList
import com.goodee.todolist.database.ToDoListDao
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ToDoListViewModel(val database: ToDoListDao, application: Application): AndroidViewModel(application) {
    private val TAG: String = "로그"
    var toDoLists: LiveData<List<ToDoList>> = database.getUnDoneList()

    fun makeNewToDoList(toDoList: ToDoList) {
        viewModelScope.launch {
            insert(toDoList)
        }
    }
    private suspend fun insert(toDoList: ToDoList) {
        database.insertToDo(toDoList)
    }

    fun deleteAllToDoList() {
        viewModelScope.launch {
            deleteAll()
        }
    }
    private suspend fun deleteAll() {
        database.deleteAllToDo()
    }

    fun getUnDoneToDoList() {
        viewModelScope.launch {
            toDoLists = getUnDoneList()
            Log.d(TAG,"ToDoListViewModel - toDoLists : ${toDoLists.value}")
        }
    }
    private suspend fun getUnDoneList(): LiveData<List<ToDoList>> {
        return database.getUnDoneList()
    }


    fun getDoneToDoList() {
        viewModelScope.launch {
            toDoLists = getDoneList()
            Log.d(TAG,"ToDoListViewModel - toDoLists : ${toDoLists.value}")
        }
    }
    private suspend fun getDoneList(): LiveData<List<ToDoList>> {
        return database.getDoneList()
    }

    fun doneToDoList(primaryKey: Int) {
        viewModelScope.launch {
            doneTodoList(primaryKey)
        }
    }
    private suspend fun doneTodoList(primaryKey: Int) {
        database.doneToDoList(primaryKey)
    }
}