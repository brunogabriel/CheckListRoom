package io.github.brunogabriel.checklistapp.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.brunogabriel.checklistapp.R
import io.github.brunogabriel.checklistapp.shared.database.model.Task
import kotlinx.android.synthetic.main.holder_task.view.*

/**
 * Created by brunosantos on 08/11/17.
 */
class TaskAdapter(private val tasks: MutableList<Task>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.checkbox.isChecked = task.completed
        holder.title.text = task.title
    }

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.holder_task, parent, false))
    }

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title = itemView.title
        var checkbox = itemView.checkbox
    }

    fun createTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun updateTask(task: Task, position: Int) {
        if (hasValidPosition(position)) {
            tasks.set(position, task)
            notifyItemChanged(position)
        }
    }

    fun deleteTask(position: Int) {
        if (hasValidPosition(position)) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun hasValidPosition(position: Int) = position >= 0 && position < tasks.size
}