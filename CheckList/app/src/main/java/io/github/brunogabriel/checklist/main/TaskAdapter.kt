package io.github.brunogabriel.checklist.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.brunogabriel.checklist.R
import io.github.brunogabriel.checklist.shared.database.model.Task
import kotlinx.android.synthetic.main.holder_task.view.*

/**
 * Created by brunosantos on 09/11/17.
 */
class TaskAdapter(private val tasks: MutableList<Task>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.checkbox.text = task.title
        holder.checkbox.isChecked = task.completed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.holder_task, parent, false))
    }

    override fun getItemCount() = tasks.size

    fun addTask(task: Task) {
        tasks.add(task)
    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val checkbox = itemView.checkbox
    }
}