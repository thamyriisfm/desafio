package com.example.desafio

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio.databinding.RowCadBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CadViewHolder(itemView: View, private val listener: CadListener?) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(cadastro: Cadastro) {
        val binding = RowCadBinding.bind(itemView)
        val textCamp = "${cadastro.name} - ${cadastro.age} anos"
        binding.tvName.text = textCamp

        binding.ivFavorite.setColorFilter(
            if (cadastro.favorite == true) itemView.context.getColor(R.color.yellow) else itemView.context.getColor(
                R.color.black
            )
        )
        binding.ivFavorite.setOnClickListener {
            listener?.favorite(cadastro)
        }
        binding.cvCard.setOnClickListener {
            listener?.edit(cadastro)
        }
        binding.cvCard.setOnLongClickListener {
            MaterialAlertDialogBuilder(itemView.context)
                .setTitle("Remover Contato")
                .setMessage("Deseja realmente excluir?")
                .setPositiveButton("Confirmar") { _, _ -> listener?.delete(cadastro) } // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                .setNeutralButton("Cancelar", null)
                .show()
            true
        }
    }
}