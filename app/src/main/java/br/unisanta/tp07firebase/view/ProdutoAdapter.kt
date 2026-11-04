package br.unisanta.tp07firebase.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.tp07firebase.databinding.ItemProdutoBinding
import br.unisanta.tp07firebase.model.Produto

class ProdutoAdapter(
    private var produtos: List<Produto> = emptyList()
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    class ProdutoViewHolder(val binding: ItemProdutoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val binding = ItemProdutoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProdutoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.binding.tvItemNome.text = produto.nome
        holder.binding.tvItemCategoria.text = "Categoria: ${produto.categoria}"
        holder.binding.tvItemPreco.text = String.format("R$ %.2f", produto.preco)
    }

    override fun getItemCount(): Int = produtos.size

    fun atualizarLista(novaLista: List<Produto>) {
        produtos = novaLista
        notifyDataSetChanged()
    }
}