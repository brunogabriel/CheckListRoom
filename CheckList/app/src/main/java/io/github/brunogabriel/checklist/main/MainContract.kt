package io.github.brunogabriel.checklist.main

import io.github.brunogabriel.checklist.shared.component.BaseView
import io.github.brunogabriel.checklist.shared.database.model.Task

/**
 * Created by brunosantos on 09/11/17.
 */
interface MainContract {
    interface View: BaseView<Presenter> {
        fun showEmptyTasks()
        fun showTasks(tasks: MutableList<Task>)
        fun showTask(task: Task)
        fun showCreateTask()
        fun showUpdateTask(task: Task?, position: Int?)
        fun showLoadError()
        fun showCreateTaskError()
        fun refreshTask(task: Task, position: Int)
        fun showUpdateTaskError()
        fun removeTask(position: Int)
        fun showRemoveTaskError()
    }

    interface Presenter {
        fun initialize()
        fun onAddTask()
        fun createTask(task: Task)
        fun tryToUpdateTask(task: Task?, position: Int?)
        fun updateTask(task: Task, position: Int?, delete: Boolean = false)
        fun checkTask(task: Task, position: Int)
        fun verifyIfListIsEmpty(numberOfItens: Int)
    }
}