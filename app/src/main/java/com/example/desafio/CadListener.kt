package com.example.desafio

interface CadListener {
    fun edit(cadastro: Cadastro)
    fun delete(cadastro: Cadastro)
    fun favorite(cadastro: Cadastro)
}