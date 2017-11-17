package io.github.brunogabriel.checklist.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import io.github.brunogabriel.checklist.R
import io.github.brunogabriel.checklist.main.creator.TaskCreatorActivity
import io.github.brunogabriel.checklist.shared.component.CustomApplication
import io.github.brunogabriel.checklist.shared.component.bindView
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.reactivex.functions.BiConsumer
import org.jetbrains.anko.toast

/**
 * Created by brunosantos on 09/11/17.
 */
class MainActivity: AppCompatActivity(), MainContract.View {
    companion object {
        const val CREATE = 1000
        const val UPDATE = 1001
        const val TASK_EXTRA = "TASK_EXTRA"
        const val POSITION_EXTRA = "TASK_POSITION"
    }

    override lateinit var presenter: MainContract.Presenter
    val recyclerView: RecyclerView by bindView(R.id.recycler_view)
    val fabAdd: FloatingActionButton by bindView(R.id.fab_add)
    val emptyView: View by bindView(R.id.empty_view)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    var taskAdapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(getString(R.string.app_name))
        fabAdd.setOnClickListener {
            presenter.onAddTask()
        }
        presenter = MainPresenter(this, CustomApplication.appDatabase?.taskDao())
        presenter.initialize()
    }

    private fun createTaskAdapter(tasks: MutableList<Task>): TaskAdapter {
        return TaskAdapter(tasks,
                BiConsumer {task, position -> presenter.tryToUpdateTask(task, position)},
                BiConsumer { task, position -> presenter.checkTask(task, position) })
    }

    // View Methods
    override fun showEmptyTasks() {
        emptyView.visibility = VISIBLE
    }

    override fun showTasks(tasks: MutableList<Task>) {
        taskAdapter = createTaskAdapter(tasks)
        recyclerView.adapter = taskAdapter
    }

    override fun showLoadError() {
        if (taskAdapter == null) {
            taskAdapter = createTaskAdapter(mutableListOf())
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
        emptyView.visibility = GONE
        taskAdapter!!.addTask(task)
    }

    override fun showCreateTaskError() {
        toast(getString(R.string.tasks_list_insert_error))
    }

    override fun refreshTask(task: Task, position: Int) {
        taskAdapter?.refreshTask(task, position)
    }

    override fun showUpdateTaskError() {
        toast(getString(R.string.task_update_error))
    }

    override fun showUpdateTask(task: Task?, position: Int?) {
        startActivityForResult(Intent(this, TaskCreatorActivity::class.java).putExtra(TASK_EXTRA, task).putExtra(POSITION_EXTRA, position), UPDATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val task: Task = data.getParcelableExtra(TASK_EXTRA)
            val position: Int? = data.getIntExtra(POSITION_EXTRA, -1)
            when(requestCode) {
                CREATE -> {
                    presenter.createTask(task)
                }
                UPDATE -> {
                    presenter.updateTask(task, position)
                }
            }
        }
    }
}