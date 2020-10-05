package com.mundris.rtu_android_final

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val db get() = Database.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getLongExtra(MainActivity.EXTRA_ID, 0)
        val item = db.notesItemDao().getItemById(id)

        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        sendMail.setOnClickListener{
            onSupportNavigateUp()

            val sendIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("email@email.lv"))
                putExtra(Intent.EXTRA_SUBJECT, item.title)
                putExtra(Intent.EXTRA_TEXT, item.note)
            }
            startActivity(sendIntent)
        }


        detailsNameInput.setText(item.title)
        NotesEditText.setText(item.note)

    }
    override fun onSupportNavigateUp(): Boolean {
        val id = intent.getLongExtra(MainActivity.EXTRA_ID, 0)
        val item = db.notesItemDao().getItemById(id)
        db.notesItemDao().update(
            item.copy(
                title = detailsNameInput.text.toString(),
                note = NotesEditText.text.toString()
            )
        )
        val intent = Intent().putExtra(MainActivity.EXTRA_ID, item.uid)
        setResult(RESULT_OK, intent)
        finish()

        return true
    }
}