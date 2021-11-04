package com.example.desafio

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Cadastro(
    var codigo: String = UUID.randomUUID().toString(),
    var name: String? = null,
    var age: Int? = null,
    var favorite: Boolean? = null
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cadastro

        if (codigo != other.codigo) return false

        return true
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }
}