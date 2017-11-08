package io.github.brunogabriel.checklistapp.main

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import io.github.brunogabriel.checklistapp.R
import io.github.brunogabriel.checklistapp.shared.CustomApplication
import io.github.brunogabriel.checklistapp.shared.component.bindView
import io.github.brunogabriel.checklistapp.shared.database.model.Task
import org.jetbrains.anko.toast

/**
 * Created by brunosantos on 08/11/17.
 */
class MainActivity: AppCompatActivity(), MainContract.View {
    val recyclerView: RecyclerView by bindView(R.id.recycler_view)
    val fabAdd: FloatingActionButton by bindView(R.id.fab_add)
    val emptyView: View by bindView(R.id.empty_view)
    var taskAdapter: TaskAdapter? = null

    override lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, CustomApplication.appDatabase?.taskDao())
        presenter.initialize()
    }

    override fun showEmptyList() {
        emptyView.visibility = VISIBLE
    }

    override fun showTasks(tasks: MutableList<Task>) {
        emptyView.visibility = GONE
        startTaskAdapter(tasks)
    }

    override fun showLoadError() {
        toast(getString(R.string.tasks_list_error_message))
    }

    override fun showCreateTask(task: Task) {
        if (taskAdapter == null) {
            startTaskAdapter(mutableListOf())
        }
        taskAdapter?.createTask(task)
    }

    override fun showCreateError(name: String) {
        toast(getString(R.string.tasks_list_insert_error, name))
    }

    private fun startTaskAdapter(tasks: MutableList<Task>) {
        taskAdapter = TaskAdapter(tasks)
        recyclerView.adapter = taskAdapter
    }
}