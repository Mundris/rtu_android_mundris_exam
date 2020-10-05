package com.mundris.rtu_android_final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_keepnotes.view.*


class NotesItemRecyclerAdapter(
    private val listener: AdapterClickListener,
    private val items: MutableList<NoteItem>
) :
    RecyclerView.Adapter<NotesItemRecyclerAdapter.ShoppingViewHolder>() {

    class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_keepnotes, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.noteName.text=item.title
        holder.itemView.noteText.text = item.note


        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.noteRemove.setOnClickListener {
            listener.deleteClicked(items[position])
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}