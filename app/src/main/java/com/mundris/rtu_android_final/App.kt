package com.mundris.rtu_android_final

import android.app.Application
import androidx.room.Room


class App : Application() {

    val db by lazy {
        Room.databaseBuilder(this, NotesDatabase::class.java, "notes-db")
            .allowMainThreadQueries()
            .build()
    }
}