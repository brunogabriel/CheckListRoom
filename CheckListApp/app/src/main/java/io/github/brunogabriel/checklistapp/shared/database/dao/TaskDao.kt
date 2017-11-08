package io.github.brunogabriel.checklistapp.shared.database.dao

import android.arch.persistence.room.*
import io.github.brunogabriel.checklistapp.shared.database.model.Task
import io.reactivex.Flowable

/**
 * Created by brunosantos on 08/11/17.
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun findAll(): Flowable<MutableList<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun findById(id: Long): Flowable<Task>

    @Insert
    fun insert(task: Task): Long

    @Update
    fun update(task: Task): Int

    @Delete
    fun delete(task: Task)
}