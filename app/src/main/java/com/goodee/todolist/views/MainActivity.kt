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
    private val database by lazy { ToDoListDatabase.getInstance(application).toDoListDao}
    private val viewModelFactory by lazy { ToDoListViewModelFactory(database, application)}
    private val viewModel: ToDoListViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(ToDoListViewModel::class.java)}
    private val recyclerView by lazy {binding.recyclerviewMainTodolists}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val toDoListAdapter = ToDoListAdapter(viewModel.toDoLists
            ,fun(primaryKey: Int) {
            doneToDoList(primaryKey)
        })
        recyclerView.adapter = toDoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.toDoLists.observe(this, Observer {
            toDoListAdapter.toDoLists = viewModel.toDoLists
            recyclerView.adapter = toDoListAdapter
        })

        binding.buttonMainCreatetodo.setOnClickListener {
            val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }
            if (binding.edittextMainTitleinput.text.isNullOrEmpty() || binding.edittextMainTitleinput.text.isBlank()) {
                Toast.makeText(this, "제목을 확인해주세요.", Toast.LENGTH_SHORT).show()
                binding.edittextMainTitleinput.requestFocus()

                imm.showSoftInput(binding.edittextMainTitleinput,0)
            } else if (binding.edittextMainContentinput.text.isNullOrEmpty() || binding.edittextMainContentinput.text.isBlank()) {
                Toast.makeText(this, "내용을 확인해주세요.", Toast.LENGTH_SHORT).show()
                binding.edittextMainContentinput.requestFocus()

                imm.showSoftInput(binding.edittextMainContentinput, 0)
            } else {
                val toDo = ToDoList(title = binding.edittextMainTitleinput.text.toString()
                    ,content = binding.edittextMainContentinput.text.toString()
                )
                binding.edittextMainTitleinput.text.clear()
                binding.edittextMainContentinput.text.clear()

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