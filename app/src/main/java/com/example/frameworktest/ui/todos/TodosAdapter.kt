package com.example.frameworktest.ui.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.model.Todo
import kotlinx.android.synthetic.main.adapter_base.view.*

class TodosAdapter(
    private val todos: List<Todo>
) : RecyclerView.Adapter<TodosAdapter.TodosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_base, parent,false)
        return TodosViewHolder(view)
    }

    override fun getItemCount(): Int = todos.count()

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        holder.bindView(todos[position])
    }

    class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.tvTitle
        private val userId = itemView.tvUserId
        private val id = itemView.tvId
        private val body = itemView.tvBody
        private val completed = itemView.tvCompleted

        fun bindView(todo: Todo){
            title.text = todo.title
            userId.text = todo.userId.toString()
            id.text = todo.id.toString()
            completed.text = todo.completed.toString()
            body.isGone = true
        }
    }
}