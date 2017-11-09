package io.github.brunogabriel.checklist.shared.database.dao

import android.arch.persistence.room.*
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.reactivex.Flowable

/**
 * Created by brunosantos on 09/11/17.
 */
@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task): Long

    @Query("SELECT * FROM tasks")
    fun findAll(): Flowable<MutableList<Task>>

    @Update
    fun update(task: Task): Int

    @Delete
    fun delete(task: Task)
}