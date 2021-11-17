package com.example.desafio

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class CadastroService {

    private val db = Firebase.firestore
    private val collection = db.collection(COLLECTION_NAME)

    fun save(cadastro: Cadastro): Task<Void> {
        return collection.document(cadastro.codigo)
            .set(cadastro)
    }

    fun edit(cadastro: Cadastro): Task<Void> {
        return collection.document(cadastro.codigo).update(
            NAME,
            cadastro.name,
            AGE,
            cadastro.age,
            FAVORITE,
            cadastro.favorite
        )
    }

    fun editFavorite(cadastro: Cadastro): Task<Void> {
        val campoNovo = cadastro.favorite != true
        return collection.document(cadastro.codigo).update(
            FAVORITE,
            campoNovo
        )
    }

    fun remove(cadastro: Cadastro): Task<Void> {
        return collection.document(cadastro.codigo).delete()
    }

    suspend fun getAll(): Task<List<Cadastro>> {
        return collection.get().continueWith {
            val lista = it.result.toObjects<Cadastro>()
            lista
        }
    }

    companion object {
        const val COLLECTION_NAME = "pessoas"
        const val NAME = "name"
        const val AGE = "age"
        const val FAVORITE = "favorite"
    }
}