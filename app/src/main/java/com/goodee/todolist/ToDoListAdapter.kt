package com.goodee.todolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.goodee.todolist.database.ToDoList

class ToDoListAdapter(private val toDoLists: LiveData<List<ToDoList>>):
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {
    private val TAG: String = "로그"
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val content: TextView
        val isFinish: CheckBox
        init {
            title = view.findViewById(R.id.textview_item_title)
            content = view.findViewById(R.id.textview_item_content)
            isFinish = view.findViewById(R.id.checkbox_item_isfinish)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.ViewHolder {
        Log.d(TAG,"ToDoListAdapter - onCreateViewHolder() called")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todolist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoListAdapter.ViewHolder, position: Int) {
        Log.d(TAG,"ToDoListAdapter - onBindViewHolder() called")
        val list: List<ToDoList>? = toDoLists.value
        list?.forEach { toDoList ->
            holder.title.text = toDoList.title
            holder.content.text = toDoList.content
            holder.isFinish.isChecked = toDoList.isFinish
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"ToDoListAdapter - getItemCount() called")
        return toDoLists.value?.size ?: 0
    }
}