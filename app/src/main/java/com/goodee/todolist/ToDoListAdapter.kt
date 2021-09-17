package com.goodee.todolist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.goodee.todolist.database.ToDoList
import com.goodee.todolist.databinding.ItemTodolistBinding

class ToDoListAdapter(var toDoLists: LiveData<List<ToDoList>>,
                      private val doneTodoList: ((Int) -> Unit)?
                      ):
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {
    private val TAG: String = "로그"

    inner class ViewHolder(binding: ItemTodolistBinding) : RecyclerView.ViewHolder(binding.root) {
        val primaryKey = binding.textviewItemPrimarykey
        val title = binding.textviewItemTitle
        val content = binding.textviewItemContent
        val isFinish = binding.checkboxItemIsfinish
        init {
            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "클릭 된 아이 = ${binding.textviewItemPrimarykey.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemTodolistBinding>(LayoutInflater.from(parent.context),R.layout.item_todolist, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoListAdapter.ViewHolder, position: Int) {
        val list: List<ToDoList>? = toDoLists.value

        list?.apply {
            holder.primaryKey.text = this.get(position).todolistPrimaryKey.toString()
            holder.title.text = this.get(position).title
            holder.content.text = this.get(position).content
            holder.isFinish.isChecked = this.get(position).isFinish
        }
        holder.isFinish.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val deletedNo = holder.primaryKey.text.toString().toInt()
                if (doneTodoList != null) {
                    doneTodoList.invoke(deletedNo)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"ToDoListAdapter - getItemCount() called")
        return toDoLists.value?.size ?: 0
    }
}