package com.goodee.todolist.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodee.todolist.R
import com.goodee.todolist.ToDoListAdapter
import com.goodee.todolist.database.ToDoList
import com.goodee.todolist.database.ToDoListDatabase
import com.goodee.todolist.databinding.ActivityMainBinding
import com.goodee.todolist.viewmodels.ToDoListViewModel
import com.goodee.todolist.viewmodels.ToDoListViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ToDoListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = this.application
        val database = ToDoListDatabase.getInstance(application).toDoListDao

        val viewModelFactory = ToDoListViewModelFactory(database, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ToDoListViewModel::class.java)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        var toDoListAdapter = ToDoListAdapter(viewModel.toDoLists, fun(primaryKey: Int) {
            Toast.makeText(this, "${primaryKey} : done to do list", Toast.LENGTH_SHORT).show()
            doneToDoList(primaryKey)
        })
        val recyclerView = binding.recyclerviewMainTodolists
        recyclerView.adapter = toDoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.toDoLists.observe(this, Observer {
            Toast.makeText(this, "변했음",Toast.LENGTH_SHORT).show()
            toDoListAdapter = ToDoListAdapter(viewModel.toDoLists,
                fun(primaryKey: Int) {
                    Toast.makeText(this, "${primaryKey} : done to do list", Toast.LENGTH_SHORT).show()
                    doneToDoList(primaryKey)
                }
            )
            recyclerView.adapter = toDoListAdapter
        })

        binding.buttonMainCreatetodo.setOnClickListener {
            if (binding.edittextMainTitleinput.text.isNullOrEmpty() || binding.edittextMainTitleinput.text.isNullOrBlank()) {
                Toast.makeText(this, "제목을 확인해주세요.", Toast.LENGTH_SHORT).show()
                binding.edittextMainTitleinput.requestFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.edittextMainTitleinput,0)
            } else if (binding.edittextMainContentinput.text.isNullOrEmpty() || binding.edittextMainContentinput.text.isNullOrBlank()) {
                Toast.makeText(this, "내용을 확인해주세요.", Toast.LENGTH_SHORT).show()
                binding.edittextMainContentinput.requestFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.edittextMainContentinput, 0)
            } else {
                val toDo = ToDoList()
                toDo.title = binding.edittextMainTitleinput.text.toString()
                toDo.content = binding.edittextMainContentinput.text.toString()
                viewModel.makeNewToDoList(toDo)
            }
        }
        binding.buttonMainDeletebutton.setOnClickListener {
            viewModel.deleteAllToDoList()
        }
    }

    private fun doneToDoList(primaryKey: Int) {
        viewModel.doneToDoList(primaryKey)
    }
}