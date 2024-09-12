package com.example.notas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.R
import com.example.notas.models.Note

class NoteAdapter(
    private val noteList: ArrayList<Note>,
    private val listener: OnNoteClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position], listener)
    }

    fun itemAdded(note: Note) {
        noteList.add(0, note) // Agrega al inicio
        notifyItemInserted(0)
    }

    fun itemDeleted(note: Note) {
        val index = noteList.indexOf(note)
        noteList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemUpdated(note: Note) {
        val index = noteList.indexOf(note)
        noteList[index] = note
        notifyItemChanged(index)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var lblNoteTitle = itemView.findViewById<TextView>(R.id.lblNoteTitle)
        private var lblNoteContent = itemView.findViewById<TextView>(R.id.lblNoteContent)
        private var btnEditNoteItem = itemView.findViewById<ImageButton>(R.id.btnEditNoteItem)
        private var btnDeleteNoteItem = itemView.findViewById<ImageButton>(R.id.btnDeleteNoteItem)

        fun bind(note: Note, listener: OnNoteClickListener) {
            lblNoteTitle.text = note.title
            lblNoteContent.text = note.content
            // Aplicar color si se requiere (no aplicamos color de fondo aquí)
            itemView.setBackgroundResource(R.drawable.rounded_corner) // Aplicar fondo redondeado blanco por defecto

            btnEditNoteItem.setOnClickListener {
                listener.onNoteEditClickListener(note)
            }
            btnDeleteNoteItem.setOnClickListener {
                listener.onNoteDeleteClickListener(note)
            }
        }
    }

    interface OnNoteClickListener {
        fun onNoteEditClickListener(note: Note)
        fun onNoteDeleteClickListener(note: Note)
    }
}
