package com.mundris.rtu_android_final

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [NoteItem::class])
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesItemDao(): NotesItemDao

}

object Database {

    private var instance: NotesDatabase? = null

    fun getInstance(context: Context) = instance ?: Room.databaseBuilder(
        context.applicationContext, NotesDatabase::class.java, "notes-db"
    )
        .allowMainThreadQueries()
        .build()
        .also { instance = it }
}