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

    override fun updateTask(task: Task, position: Int?) {
        if (position != null && position >= 0) {
            Flowable.fromCallable {
                dao?.update(task)
            }.onUIAfterIO().subscribe({
                if (it >= 0) {
                    view.refreshTask(task, position)
                } else {
                    view.showUpdateTaskError()
                }
            }, {
                view.showUpdateTaskError()
            })
        } else {
            view.showUpdateTaskError()
        }
    }

    override fun checkTask(task: Task, position: Int) {
        val previousCompleted = task.completed
        if (position >= 0) {
            Flowable.fromCallable {
                dao?.update(Task(task.id, !task.completed, task.title))
            }.onUIAfterIO().subscribe({
                if (it >= 0) {
                    view.refreshTask(task, position)
                } else {
                    view.refreshTask(Task(task.id, previousCompleted, task.title), position)
                    view.showUpdateTaskError()
                }
            }, {
                view.showUpdateTaskError()
            })
        } else {
            view.showUpdateTaskError()
        }
    }

    override fun tryToUpdateTask(task: Task?, position: Int?) {
        view.showUpdateTask(task, position)
    }

}