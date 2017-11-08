package io.github.brunogabriel.checklistapp.main

import io.github.brunogabriel.checklistapp.shared.component.BaseView
import io.github.brunogabriel.checklistapp.shared.database.model.Task

/**
 * Created by brunosantos on 08/11/17.
 */
interface MainContract {
    interface View: BaseView<Presenter> {
        fun showEmptyList()
        fun showTasks(tasks: MutableList<Task>)
        fun showLoadError()
        fun showCreateTask(task: Task)
        fun showCreateError(name: String)
    }

    interface Presenter {
        fun initialize()
        fun createTask(checked: Boolean = false, name: String)
    }
}