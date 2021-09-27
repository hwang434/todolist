package com.goodee.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.reflect.Constructor

@Entity(tableName = "ToDoList")
data class ToDoList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="no")
    var todolistPrimaryKey: Long = 0,

    @ColumnInfo(name = "title")
    var title: String = "title",

    @ColumnInfo(name = "content")
    var content: String = "content",

    @ColumnInfo(name = "isFinish")
    var isFinish: Boolean = false
) {
    constructor(title: String, content: String) : this() {
        this.title = title
        this.content = content
    }
}
