package com.example.desafio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CadViewModel : ViewModel() {

    lateinit var cadastros: LiveData<List<Cadastro>>


    fun refreshData(){
       // CadAdapter().submitList(cadastros)
    }

    fun save(cadastro: Cadastro){}

}