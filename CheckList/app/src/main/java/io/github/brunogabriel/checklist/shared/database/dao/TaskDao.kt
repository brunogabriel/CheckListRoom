package io.github.brunogabriel.checklist.shared.database.dao

import android.arch.persistence.room.*
import io.github.brunogabriel.checklist.shared.database.model.Task
import io.reactivex.Single

/**
 * Created by brunosantos on 09/11/17.
 */
@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(task: Task): Long

    @Query("SELECT * FROM tasks")
    fun findAll(): Single<MutableList<Task>>

    @Update
    fun update(task: Task): Int

    @Delete
    fun delete(task: Task): Int
}