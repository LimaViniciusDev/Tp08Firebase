package br.unisanta.tp07firebase.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import br.unisanta.tp07firebase.R
import br.unisanta.tp07firebase.databinding.ActivityMainBinding
import br.unisanta.tp07firebase.model.Produto
import br.unisanta.tp07firebase.viewmodel.ProdutoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProdutoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[ProdutoViewModel::class.java]

        viewModel.salvarCliente()

        binding.btnCadastrar.setOnClickListener {
            val nome = binding.edtNome.text.toString().trim()
            val categoria = binding.edtCategoria.text.toString().trim()
            val precoStr = binding.edtPreco.text.toString().replace(",", ".").trim()

            if (nome.isEmpty() || categoria.isEmpty() || precoStr.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Preencha todos os campos antes de salvar!")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            val preco = precoStr.toDoubleOrNull() ?: 0.0
            val novoProduto = Produto(nome = nome, categoria = categoria, preco = preco)

            binding.edtNome.setText("")
            binding.edtCategoria.setText("")
            binding.edtPreco.setText("")
            binding.edtNome.requestFocus()

            AlertDialog.Builder(this)
                .setTitle("Produto Adicionado!")
                .setMessage("O produto '$nome' foi cadastrado com sucesso.")
                .setPositiveButton("OK", null)
                .show()

            viewModel.cadastrarProduto(
                produto = novoProduto,
                onSuccess = {},
                onError = { erro ->
                    AlertDialog.Builder(this)
                        .setTitle("Erro no Firestore")
                        .setMessage("Falha ao sincronizar: $erro")
                        .setPositiveButton("OK", null)
                        .show()
                }
            )
        }

        val irParaLista = {
            startActivity(Intent(this, ListaProdutosActivity::class.java))
        }
        binding.btnVerLista.setOnClickListener { irParaLista() }
        binding.fabLista.setOnClickListener { irParaLista() }
    }
}