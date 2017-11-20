package io.github.brunogabriel.checklist.main.creator

import io.github.brunogabriel.checklist.shared.database.model.Task

/**
 * Created by brunosantos on 09/11/17.
 */
class TaskCreatorPresenter(var view: TaskCreatorContract.View, var task: Task?, var position: Int?): TaskCreatorContract.Presenter {

    override fun initialize() {
        task?.let {
            view.fillUpdateTask(it.completed, it.title, it.id)
        }
    }

    override fun saveTask(checked: Boolean, title: String) {
        val id = if (task != null) task!!.id else 0
        view.showTask(Task(id, checked, title), position)
    }

    override fun deleteTask() {
        view.deleteTask(task!!, position)
    }

}