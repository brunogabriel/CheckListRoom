package io.github.brunogabriel.checklistapp.main

import io.github.brunogabriel.checklistapp.shared.database.dao.TaskDao
import io.github.brunogabriel.checklistapp.shared.database.model.Task
import io.github.brunogabriel.checklistapp.shared.extensions.onUIAfterIO
import io.reactivex.Single

/**
 * Created by brunosantos on 08/11/17.
 */
class MainPresenter(val view: MainContract.View, val dao: TaskDao?): MainContract.Presenter {

    override fun initialize() {
        dao?.findAll()?.onUIAfterIO()
                ?.subscribe({
                    if (it.isEmpty()) {
                        view.showEmptyList()
                    } else {
                        view.showTasks(it)
                    }
                }, {
                    view.showEmptyList()
                    view.showLoadError()
                })
    }

    override fun createTask(checked: Boolean, name: String) {
       Single.fromCallable {
           dao?.insert(Task(completed = checked, title = name))
       }.onUIAfterIO().subscribe({
           view.showCreateTask(Task(it, checked, name))
       }, {
            view.showCreateError(name)
       })
    }
}