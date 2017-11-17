package io.github.brunogabriel.checklist.main.creator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.CheckBox
import com.jakewharton.rxbinding2.widget.RxTextView
import io.github.brunogabriel.checklist.R
import io.github.brunogabriel.checklist.main.MainActivity.Companion.POSITION_EXTRA
import io.github.brunogabriel.checklist.main.MainActivity.Companion.TASK_EXTRA
import io.github.brunogabriel.checklist.shared.component.bindView
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by brunosantos on 09/11/17.
 */
class TaskCreatorActivity : AppCompatActivity(), TaskCreatorContract.View {
    override lateinit var presenter: TaskCreatorContract.Presenter
    val actionButton: AppCompatButton by bindView(R.id.action_button)
    val checkBox: CheckBox by bindView(R.id.checkbox)
    val taskEditText: TextInputEditText by bindView(R.id.edit_text)
    val toolbar: Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_creator)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.creator_create)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = TaskCreatorPresenter(this, intent?.extras?.getParcelable<Task>(TASK_EXTRA), intent?.extras?.getInt(POSITION_EXTRA, -1))
        presenter.initialize()

        actionButton.setOnClickListener {
            presenter.saveTask(checkBox.isChecked, taskEditText.text.toString())
        }

        RxTextView.textChanges(taskEditText)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map { it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    actionButton.isEnabled = it
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return false
    }

    override fun fillUpdateTask(completed: Boolean, title: String, id: Long) {
        checkBox.isChecked = completed
        taskEditText.setText(title)
        actionButton.setText(getString(R.string.creator_update))
        supportActionBar?.setTitle(getString(R.string.update_task_id, id))
    }

    override fun showTask(task: Task, position: Int?) {
        setResult(Activity.RESULT_OK, Intent().putExtra(TASK_EXTRA, task).putExtra(POSITION_EXTRA, position))
        finish()
    }
}