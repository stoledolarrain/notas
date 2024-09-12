package com.example.notas.ui.activities

// RecyclerViewConfigurator.kt
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewConfigurator(private val context: Context) {

    fun configureRecyclerView(recyclerView: RecyclerView) {
        val layoutParams = recyclerView.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(20, 16, 20, 16)  // Ajusta los márgenes según sea necesario
        recyclerView.layoutParams = layoutParams
    }
}
