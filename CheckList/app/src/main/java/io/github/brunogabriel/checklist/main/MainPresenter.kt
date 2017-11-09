package io.github.brunogabriel.checklist.main

import io.github.brunogabriel.checklist.shared.database.dao.TaskDao
import io.github.brunogabriel.checklist.shared.extensions.onUIAfterIO

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

}