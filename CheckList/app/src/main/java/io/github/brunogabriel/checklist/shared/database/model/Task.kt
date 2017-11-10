package io.github.brunogabriel.checklist.shared.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by brunosantos on 09/11/17.
 */
@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) var id: Long = 0, val completed: Boolean = false, val title: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeInt((if (completed) 1 else 0))
        writeString(title)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Task> = object : Parcelable.Creator<Task> {
            override fun createFromParcel(source: Parcel): Task = Task(source)
            override fun newArray(size: Int): Array<Task?> = arrayOfNulls(size)
        }
    }
}