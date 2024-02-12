package ru.je_dog.core.feature.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import ru.je_dog.core.feature.databinding.RcvProductBinding
import ru.je_dog.core.feature.model.ProductPresentation

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    var products: List<ProductPresentation> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ProductHolder(private val binding: RcvProductBinding): ViewHolder(binding.root) {
        fun bind(product: ProductPresentation) = with(binding){
            price.text = product.price
            description.text = product.description
            image.load(product.image)
            title.text = product.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = RcvProductBinding.inflate(inflater,parent,false)

        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

}