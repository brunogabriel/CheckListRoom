package io.github.brunogabriel.checklist.main

import io.github.brunogabriel.checklist.shared.database.dao.TaskDao
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.github.brunogabriel.checklist.shared.extensions.onUIAfterIO
import io.reactivex.Single

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
        Single.fromCallable {
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

    override fun updateTask(task: Task, position: Int?, delete: Boolean) {
        if (delete) {
            databaseDeleteTask(task, position)
        } else {
            databaseUpdateTask(task, position)
        }
    }

    private fun databaseDeleteTask(task: Task, position: Int?) {
        if (position != null && position >= 0) {
            Single.fromCallable {
                dao?.delete(task)
            }.onUIAfterIO().subscribe({
                if (it >= 0) {
                    view.removeTask(position)
                } else {
                    view.showRemoveTaskError()
                }
            }, {
                view.showRemoveTaskError()
            })
        } else {
            view.showRemoveTaskError()
        }
    }

    private fun databaseUpdateTask(task: Task, position: Int?) {
        if (position != null && position >= 0) {
            Single.fromCallable {
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
        val newTask = Task(task.id, !task.completed, task.title)
        if (position >= 0) {
            Single.fromCallable {
                dao?.update(newTask)
            }.onUIAfterIO().subscribe({
                if (it >= 0) {
                    view.refreshTask(newTask, position)
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

    override fun verifyIfListIsEmpty(numberOfItens: Int) {
        if (numberOfItens == 0) {
            view.showEmptyTasks()
        }
    }

}