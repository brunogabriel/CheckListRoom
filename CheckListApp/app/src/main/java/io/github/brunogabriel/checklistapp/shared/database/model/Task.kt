package io.github.brunogabriel.checklistapp.shared.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by brunosantos on 08/11/17.
 */
@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) var id: Long = 0, var completed: Boolean = false, var title: String)