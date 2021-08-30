package com.goodee.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.goodee.todolist.database.ToDoList
import com.goodee.todolist.database.ToDoListDao
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ToDoListViewModel(val database: ToDoListDao, application: Application): AndroidViewModel(application) {
    var toDoLists: LiveData<List<ToDoList>> = database.getAllToDoList()

    fun makeNewToDoList(toDoList: ToDoList) {
        viewModelScope.launch {
            insert(toDoList)
        }
    }
    fun deleteAllToDoList() {
        viewModelScope.launch {
            deleteAll()
        }
    }
    fun getDoneToDoList() {
        viewModelScope.async {
            toDoLists = getDoneList()
        }
    }

    private suspend fun insert(toDoList: ToDoList) {
        database.insertToDo(toDoList)
    }
    private suspend fun deleteAll() {
        database.deleteAllToDo()
    }
    private suspend fun getDoneList(): LiveData<List<ToDoList>> {
        return database.getDoneList()
    }
}