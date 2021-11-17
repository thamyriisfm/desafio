package com.example.desafio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.desafio.databinding.RowCadBinding

class CadAdapter(private val listener: CadListener?) : ListAdapter<Cadastro, CadViewHolder>(ItemDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CadViewHolder {
        val item = RowCadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CadViewHolder(item.root, listener)
    }

    override fun onBindViewHolder(holder: CadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ItemDiff : DiffUtil.ItemCallback<Cadastro>() { // usar classe ou object ?
    override fun areItemsTheSame(oldItem: Cadastro, newItem: Cadastro): Boolean {
        return oldItem.codigo == newItem.codigo
    }

    override fun areContentsTheSame(oldItem: Cadastro, newItem: Cadastro): Boolean {
        return (oldItem.name == newItem.name && oldItem.age == newItem.age && oldItem.favorite == newItem.favorite)
    }
}