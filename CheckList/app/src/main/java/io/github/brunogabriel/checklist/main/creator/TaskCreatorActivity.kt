package io.github.brunogabriel.checklist.main.creator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.brunogabriel.checklist.R

/**
 * Created by brunosantos on 09/11/17.
 */
class TaskCreatorActivity : AppCompatActivity(), TaskCreatorContract.View {

    override lateinit var presenter: TaskCreatorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_creator)
    }
}