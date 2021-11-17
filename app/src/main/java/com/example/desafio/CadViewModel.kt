package com.example.desafio

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.*

class CadViewModel : ViewModel() {

    val cadastros = MutableLiveData<List<Cadastro>>()

    //private lateinit var cadService: CadastroService

    fun refreshData() {
        viewModelScope.launch {
            try {
                val newCad = withContext(Dispatchers.IO) {
                    CadastroService().getAll().result
                }
                if (newCad != cadastros.value){
                    cadastros.value = newCad
                }
            }catch (e: Exception) {
                Log.w(TAG,"",e)
            }
        }
    }

    fun save(cadastro: Cadastro) {
        viewModelScope.launch {
            try {
                if (cadastro.codigo.isBlank()) {
                    withContext(Dispatchers.IO) {
                        CadastroService().save(cadastro)
                    }
                } else {
                    withContext(Dispatchers.IO) {
                        CadastroService().edit(cadastro)
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Algo deu errado", e)
            }
        }
    }
}