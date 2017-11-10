package io.github.brunogabriel.checklist.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.VISIBLE
import io.github.brunogabriel.checklist.R
import io.github.brunogabriel.checklist.main.creator.TaskCreatorActivity
import io.github.brunogabriel.checklist.shared.component.CustomApplication
import io.github.brunogabriel.checklist.shared.component.bindView
import io.github.brunogabriel.checklist.shared.database.model.Task
import org.jetbrains.anko.toast

/**
 * Created by brunosantos on 09/11/17.
 */
class MainActivity: AppCompatActivity(), MainContract.View {

    companion object {
        const val CREATE = 1000
        const val UPDATE = 1001
        const val TASK_EXTRA = "TASK_EXTRA"
    }

    override lateinit var presenter: MainContract.Presenter
    val recyclerView: RecyclerView by bindView(R.id.recycler_view)
    val fabAdd: FloatingActionButton by bindView(R.id.fab_add)
    val emptyView: View by bindView(R.id.empty_view)
    var taskAdapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabAdd.setOnClickListener {
            presenter.onAddTask()
        }
        presenter = MainPresenter(this, CustomApplication.appDatabase?.taskDao())
        presenter.initialize()
    }

    // View Methods
    override fun showEmptyTasks() {
        emptyView.visibility = VISIBLE
    }

    override fun showTasks(tasks: MutableList<Task>) {
        taskAdapter = TaskAdapter(tasks)
        recyclerView.adapter = taskAdapter
    }

    override fun showLoadError() {
        if (taskAdapter == null) {
            taskAdapter = TaskAdapter(mutableListOf())
        }
        toast(getString(R.string.tasks_list_error_message))
    }

    override fun showCreateTask() {
        startActivityForResult(Intent(this, TaskCreatorActivity::class.java), CREATE)
    }

    override fun showTask(task: Task) {
        if (taskAdapter == null) {
            showTasks(mutableListOf())
        }
        taskAdapter!!.addTask(task)
    }

    override fun showCreateTaskError() {
        toast(getString(R.string.tasks_list_insert_error))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val task: Task = data.getParcelableExtra(TASK_EXTRA)
            when(requestCode) {
                CREATE -> {
                    presenter.createTask(task)
                }
            }
        }
    }
}