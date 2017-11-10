package io.github.brunogabriel.checklist.main

import io.github.brunogabriel.checklist.shared.database.dao.TaskDao
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.github.brunogabriel.checklist.shared.extensions.onUIAfterIO
import io.reactivex.Flowable

/**
 * Created by brunosantos on 09/11/17.
 */
class MainPresenter(var view: MainContract.View, var dao: TaskDao?): MainContract.Presenter {

    override fun initialize() {
        dao?.findAll()?.onUIAfterIO()
                ?.subscribe({
                    if (it.isEmpty()) {
                        view.showEmptyTasks()
                    } else {
                        view.showTasks(it)
                    }
                }, {
                    view.showEmptyTasks()
                    view.showLoadError()
                })
    }

    override fun onAddTask() {
        view.showCreateTask()
    }

    override fun createTask(task: Task) {
        Flowable.fromCallable {
            dao?.insert(task)
        }.onUIAfterIO().subscribe({
            if (it >= 0) {
                task.id = it
                view.showTask(task)
            } else {
                view.showCreateTaskError()
            }
        }, {
            view.showCreateTaskError()
        })
    }
}