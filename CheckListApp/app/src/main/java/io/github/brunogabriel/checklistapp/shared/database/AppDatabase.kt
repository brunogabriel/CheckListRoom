package io.github.brunogabriel.checklistapp.shared.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.brunogabriel.checklistapp.shared.database.dao.TaskDao
import io.github.brunogabriel.checklistapp.shared.database.model.Task

/**
 * Created by brunosantos on 08/11/17.
 */
@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}