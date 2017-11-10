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
        fun showLoadError()
        fun showCreateTaskError()
    }

    interface Presenter {
        fun initialize()
        fun onAddTask()
        fun createTask(task: Task)
    }
}