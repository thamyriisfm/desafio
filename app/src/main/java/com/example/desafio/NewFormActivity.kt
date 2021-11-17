package com.example.desafio

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio.databinding.ActivityNewFormBinding

class NewFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewFormBinding
    private var mId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val registro: Cadastro? = bundle.getParcelable(CadastrosConstants.CADASTRO_ARGS)
            mId = registro?.codigo ?: ""
            binding.etName.setText(registro?.name ?: "sem nome")
            binding.etAge.setText(registro?.age.toString())
            when (registro?.favorite) {
                true -> binding.cbFavorite.isChecked = true
                false -> binding.cbFavorite.isChecked = false
            }
        }

        binding.btSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        if (validate(binding.etName.text.toString(), binding.etAge.text.toString())) {

            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            if (mId.isBlank()) {
                val registro = Cadastro(
                    name = binding.etName.text.toString(),
                    age = binding.etAge.text.toString().toInt(),
                    favorite = binding.cbFavorite.isChecked
                )
                bundle.putParcelable(CadastrosConstants.CADASTRO_ARGS, registro)
                intent.putExtras(bundle)
                Toast.makeText(this, "Deu certo!", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                val registro = Cadastro(
                    mId,
                    binding.etName.text.toString(),
                    binding.etAge.text.toString().toInt(),
                    binding.cbFavorite.isChecked
                )
                bundle.putParcelable(CadastrosConstants.CADASTRO_ARGS, registro)
                intent.putExtras(bundle)
                Toast.makeText(this, "Deu certo!", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        } else {
            Toast.makeText(this, "Todos os campos precisam ser preenchidos!!!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validate(name: String, age: String): Boolean {
        return name.isNotBlank() && age.isNotBlank()
    }
}