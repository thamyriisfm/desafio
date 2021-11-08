package com.example.desafio

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cadViewModel: CadViewModel

    private val mListener: CadListener = object : CadListener {
        override fun edit(cadastro: Cadastro) {
            val intent = Intent(this@MainActivity, FormActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(CadastrosConstants.CADASTRO_ARGS, cadastro)
            intent.putExtras(bundle)
            cadLauncher.launch(intent)
        }

        override fun delete(cadastro: Cadastro) {
            lifecycleScope.launchWhenCreated {
                try {
                    withContext(Dispatchers.IO) {
                        CadastroService().remove(cadastro)//.await()
                        cadViewModel.refreshData() // <<<<<< não dava ceto sem isso
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "NÃO DEU CERTO !!! sz !!!", e)
                }
            }
        }

        override fun favorite(cadastro: Cadastro) {
            lifecycleScope.launchWhenCreated {
                try {
                    withContext(Dispatchers.IO) {
                        CadastroService().editFavorite(cadastro)//.await()
                    }
                    cadViewModel.refreshData()
                } catch (e: Exception) {
                    Log.w(TAG, "QUANDO NÃO DESFAVORITA/ FAVORITA", e)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cadViewModel = ViewModelProvider(this)[CadViewModel::class.java]

        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = CadAdapter(mListener)

        val viewModel: CadViewModel = cadViewModel
        viewModel.cadastros.observe(this) {
            notificar(it)
        }
        cadViewModel.refreshData()

        binding.btAdd.setOnClickListener {
            cadLauncher.launch(Intent(this, FormActivity::class.java))
        }
    }

    private fun notificar(lista: List<Cadastro>) {
        (binding.rvList.adapter as CadAdapter).submitList(lista.toList())
    }

    private val cadLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val cadTeste: Cadastro? =
                result.data?.extras?.getParcelable(CadastrosConstants.CADASTRO_ARGS)
            cadTeste?.let { registro ->
                cadViewModel.save(registro)
            }
            cadViewModel.refreshData()
        }
    }
}