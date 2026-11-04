package br.unisanta.tp07firebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.unisanta.tp07firebase.model.Produto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class ProdutoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val colecao = db.collection("Produtos")

    private val _listaProdutos = MutableLiveData<List<Produto>>()
    val listaProdutos: LiveData<List<Produto>> = _listaProdutos

    fun cadastrarProduto(
        produto: Produto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val dados = hashMapOf(
            "Nome" to produto.nome,
            "Categoria" to produto.categoria,
            "Preço" to produto.preco
        )

        colecao.add(dados)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Erro ao salvar produto")
            }
    }

    fun carregarProdutos() {
        colecao.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                return@addSnapshotListener
            }

            val produtos = snapshot.documents.map { doc ->
                val nome = doc.getString("Nome") ?: doc.getString("nome") ?: ""
                val categoria = doc.getString("Categoria") ?: doc.getString("categoria") ?: ""
                val preco = doc.getDouble("Preço") ?: doc.getDouble("preco") ?: 0.0

                Produto(nome = nome, categoria = categoria, preco = preco)
            }

            _listaProdutos.value = produtos
        }
    }

    fun salvarCliente() {
        val stamp = Timestamp.now()
        val hashCliente = hashMapOf(
            "nome" to "Natália",
            "cpf" to "333.222.111-00",
            "ultima_compra" to stamp
        )

        db.collection("clientes")
            .document("cliente_natalia")
            .set(hashCliente)
    }
}