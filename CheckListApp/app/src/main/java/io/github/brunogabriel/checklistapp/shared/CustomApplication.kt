package io.github.brunogabriel.checklistapp.shared

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import io.github.brunogabriel.checklistapp.shared.database.AppDatabase

/**
 * Created by brunosantos on 08/11/17.
 */
class CustomApplication: Application() {

    companion object {
        var appDatabase: AppDatabase? = null
        private fun createDatabase(context: Context) {
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "checklist.db").build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        createDatabase(this)
    }
}