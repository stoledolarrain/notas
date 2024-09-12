package com.example.notas.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.R
import com.example.notas.models.Note
import com.example.notas.ui.adapters.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteAdapter.OnNoteClickListener {
    private val noteList = arrayListOf<Note>()
    private lateinit var rvNoteList: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fabAddNote = findViewById(R.id.fabAddNote)
        rvNoteList = findViewById(R.id.rvNoteList)

        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        fabAddNote.setOnClickListener {
            buildAlertDialog()
        }
    }

    private fun setupRecyclerView() {
        rvNoteList.adapter = NoteAdapter(noteList, this)
        rvNoteList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun buildAlertDialog(note: Note? = null) {
        val builder = AlertDialog.Builder(this)

        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.form_layout, null, false)

        val txtNoteTitle: EditText = viewInflated.findViewById(R.id.txtNoteTitle)
        val txtNoteContent: EditText = viewInflated.findViewById(R.id.txtNoteContent)
        val imageButtonColorPicker: ImageButton = viewInflated.findViewById(R.id.btnChangeColor) // Botón para seleccionar color

        txtNoteTitle.setText(note?.title)
        txtNoteContent.setText(note?.content)

        // Mostrar la selección de colores al hacer clic en el botón
        imageButtonColorPicker.setOnClickListener {
            showColorPickerDialog { selectedColor ->
                note?.color = selectedColor
                imageButtonColorPicker.setBackgroundColor(selectedColor)
            }
        }

        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val title = txtNoteTitle.text.toString()
            val content = txtNoteContent.text.toString()

            if (note != null) {
                note.title = title
                note.content = content
                editNoteFromList(note)
            } else {
                val newNote = Note(title, content, note?.color ?: Color.WHITE) // Por defecto blanco si no se ha seleccionado un color
                addNoteToList(newNote)
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showColorPickerDialog(onColorSelected: (Int) -> Unit) {
        val builder = AlertDialog.Builder(this)
        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.color_picker_layout, null, false)

        builder.setView(viewInflated)

        val btnColor1: ImageButton = viewInflated.findViewById(R.id.btnColor1)
        val btnColor2: ImageButton = viewInflated.findViewById(R.id.btnColor2)
        val btnColor3: ImageButton = viewInflated.findViewById(R.id.btnColor3)
        val btnColor4: ImageButton = viewInflated.findViewById(R.id.btnColor4)
        val btnColor5: ImageButton = viewInflated.findViewById(R.id.btnColor5)
        val btnColor6: ImageButton = viewInflated.findViewById(R.id.btnColor6)
        val btnColor7: ImageButton = viewInflated.findViewById(R.id.btnColor7)
        val btnColor8: ImageButton = viewInflated.findViewById(R.id.btnColor8)
        val btnColor9: ImageButton = viewInflated.findViewById(R.id.btnColor9)
        val btnColor10: ImageButton = viewInflated.findViewById(R.id.btnColor10)

        // Asignar color a los botones y agregar los listeners
        btnColor1.setOnClickListener { onColorSelected(Color.RED) }
        btnColor2.setOnClickListener { onColorSelected(Color.GREEN) }
        btnColor3.setOnClickListener { onColorSelected(Color.BLUE) }
        btnColor4.setOnClickListener { onColorSelected(Color.YELLOW) }
        btnColor5.setOnClickListener { onColorSelected(Color.MAGENTA) }
        btnColor6.setOnClickListener { onColorSelected(Color.CYAN) }
        btnColor7.setOnClickListener { onColorSelected(Color.WHITE) }
        btnColor8.setOnClickListener { onColorSelected(Color.BLACK) }
        btnColor9.setOnClickListener { onColorSelected(Color.parseColor("#FFA500")) } // Naranja
        btnColor10.setOnClickListener { onColorSelected(Color.GRAY) }

        builder.setTitle("Selecciona un color")
        builder.show()
    }

    private fun editNoteFromList(note: Note) {
        val adapter = rvNoteList.adapter as NoteAdapter
        adapter.itemUpdated(note)
    }

    private fun addNoteToList(note: Note) {
        val adapter = rvNoteList.adapter as NoteAdapter
        adapter.itemAdded(note)
    }

    override fun onNoteEditClickListener(note: Note) {
        buildAlertDialog(note)
    }

    override fun onNoteDeleteClickListener(note: Note) {
        val adapter = rvNoteList.adapter as NoteAdapter
        adapter.itemDeleted(note)
    }
}