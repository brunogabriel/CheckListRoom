package io.github.brunogabriel.checklist.shared.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.brunogabriel.checklist.shared.database.dao.TaskDao
import io.github.brunogabriel.checklist.shared.database.model.Task

/**
 * Created by brunosantos on 09/11/17.
 */
@Database(version = 1, entities = arrayOf(Task::class), exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}