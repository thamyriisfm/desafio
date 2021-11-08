package com.example.desafio

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CadViewModel(application: Application) : ViewModel() {

    lateinit var cadastros: LiveData<List<Cadastro>>


    fun refreshData(){

    }

    fun save(cadastro: Cadastro){}

}