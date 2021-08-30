package com.goodee.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goodee.todolist.database.ToDoListDao
import java.lang.IllegalArgumentException

class ToDoListViewModelFactory(private val database: ToDoListDao,
                               private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            return ToDoListViewModel(database, application) as T
        }

        throw IllegalArgumentException("ViewModel class not matched.")
    }

}