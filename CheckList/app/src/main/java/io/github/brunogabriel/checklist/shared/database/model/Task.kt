package io.github.brunogabriel.checklist.shared.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by brunosantos on 09/11/17.
 */
@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Long = 0, val completed: Boolean = false, val title: String)