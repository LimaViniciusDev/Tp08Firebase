package br.unisanta.tp07firebase.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.unisanta.tp07firebase.R
import br.unisanta.tp07firebase.databinding.ActivityListaProdutosBinding
import br.unisanta.tp07firebase.viewmodel.ProdutoViewModel

class ListaProdutosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaProdutosBinding
    private lateinit var viewModel: ProdutoViewModel
    private lateinit var adapter: ProdutoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[ProdutoViewModel::class.java]

        adapter = ProdutoAdapter()
        binding.rvProdutos.layoutManager = LinearLayoutManager(this)
        binding.rvProdutos.adapter = adapter

        // Observa a lista de produtos vinda do Firestore
        viewModel.listaProdutos.observe(this) { lista ->
            adapter.atualizarLista(lista)
        }

        // Inicia a busca na coleção "Produtos"
        viewModel.carregarProdutos()

        // Botão flutuante para voltar à tela de cadastro
        binding.fabVoltar.setOnClickListener {
            finish()
        }
    }
}