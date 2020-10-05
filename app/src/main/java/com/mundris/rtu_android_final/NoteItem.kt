package com.mundris.rtu_android_final

import androidx.room.*

@Entity(tableName = "note_item")
data class NoteItem(
    val title: String,
    val note: String,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface NotesItemDao {
    @Query("SELECT * FROM note_item")
    fun getAll(): List<NoteItem>

    @Query("SELECT * FROM note_item WHERE uid = :itemId")
    fun getItemById(itemId: Long): NoteItem

    @Insert
    fun insertAll(vararg items: NoteItem): List<Long>

    @Update
    fun update(item: NoteItem)

    @Delete
    fun delete(item: NoteItem)
}