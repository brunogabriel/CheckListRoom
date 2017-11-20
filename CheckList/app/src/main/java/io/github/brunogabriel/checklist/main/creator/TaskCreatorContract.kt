package io.github.brunogabriel.checklist.main.creator

import io.github.brunogabriel.checklist.shared.component.BaseView
import io.github.brunogabriel.checklist.shared.database.model.Task

/**
 * Created by brunosantos on 09/11/17.
 */
interface TaskCreatorContract {
    interface View: BaseView<Presenter> {
        fun fillUpdateTask(completed: Boolean, title: String, id: Long)
        fun showTask(task: Task, position: Int?)
        fun deleteTask(Task: Task, position: Int?)
    }

    interface Presenter {
        fun initialize()
        fun saveTask(checked: Boolean, title: String)
        fun deleteTask()
    }
}