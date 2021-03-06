package io.github.brunogabriel.checklist.main

import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.brunogabriel.checklist.R
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.holder_task.view.*

/**
 * Created by brunosantos on 09/11/17.
 */
class TaskAdapter(private val tasks: MutableList<Task>,
                  private val onUpdateSelectAction: BiConsumer<Task, Int>,
                  private val onCheckSelectAction: BiConsumer<Task, Int>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.checkbox.text = task.title
        holder.checkbox.isChecked = task.completed
        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.setOnClickListener { onCheckSelectAction.accept(task, holder.adapterPosition) }
        holder.updateImage.setOnClickListener { onUpdateSelectAction.accept(task, holder.adapterPosition) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.holder_task, parent, false))
    }

    override fun getItemCount() = tasks.size

    fun addAll(newTasks: MutableList<Task>) {
        val previousSize = tasks.size
        tasks.addAll(newTasks)
        notifyItemRangeInserted(previousSize, newTasks.size)
    }

    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size)
    }

    fun removeTask(position: Int, onAfterDelete: Consumer<Int>) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
        onAfterDelete.accept(itemCount)

    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val checkbox = itemView.checkbox
        val updateImage = itemView.update_image
    }

    fun refreshTask(task: Task, position: Int) {
        tasks[position] = task
        notifyItemChanged(position)
    }
}