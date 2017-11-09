package io.github.brunogabriel.checklist.shared.component

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import io.github.brunogabriel.checklist.shared.database.AppDatabase

/**
 * Created by brunosantos on 09/11/17.
 */
class CustomApplication: Application() {

    companion object {
        var appDatabase: AppDatabase? = null

        private fun createDatabase(context: Context) {
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        createDatabase(this)
    }
}