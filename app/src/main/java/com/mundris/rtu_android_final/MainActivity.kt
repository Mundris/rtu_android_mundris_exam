package com.mundris.rtu_android_final


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_keepnotes.*

class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val db get() = Database.getInstance(this)

    private val items = mutableListOf<NoteItem>()

    private lateinit var adapter: NotesItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items.addAll(db.notesItemDao().getAll())
        adapter = NotesItemRecyclerAdapter(this, items)
        mainItems.adapter = adapter

        FAB.setOnClickListener { appendItem() }

        switch1.setOnCheckedChangeListener {_, checkedID ->
            when (checkedID) {
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

}

    private fun appendItem() {
        val item = NoteItem("", "")
        item.uid = db.notesItemDao().insertAll(item).first()
        items.add(item)
        items.sortBy { it.title }
        adapter.notifyDataSetChanged()

        itemClicked(item)
    }

    override fun itemClicked(item: NoteItem) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(EXTRA_ID, item.uid)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteClicked(item: NoteItem) {
        db.notesItemDao().delete(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK && data != null) {
            val id = data.getLongExtra(EXTRA_ID, 0)
            val item = db.notesItemDao().getItemById(id)
            val position = items.indexOfFirst { it.uid == item.uid }
            items[position] = item
            adapter.notifyItemChanged(position)
        }
    }

    companion object {
        const val EXTRA_ID = "com.mundris.rtu_android_final.EXTRA_ID"
        const val REQUEST_CODE_DETAILS = 1234
    }
}

interface AdapterClickListener {

    fun itemClicked(item: NoteItem)

    fun deleteClicked(item: NoteItem)

}