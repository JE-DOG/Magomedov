package ru.je_dog.core.feature.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import ru.je_dog.core.feature.databinding.RcvFilmBinding
import ru.je_dog.core.feature.model.FilmPresentation

class FilmAdapter(
    private val onLongClick: (FilmPresentation) -> Unit
): RecyclerView.Adapter<FilmAdapter.ProductHolder>() {

    var products: List<FilmPresentation> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ProductHolder(private val binding: RcvFilmBinding): ViewHolder(binding.root) {
        fun bind(film: FilmPresentation) = with(binding){
            name.text = film.nameRu
            genreAndDate.text = film. genreAndDate
            image.load(film.posterUrlPreview)
            if (film.isFavorite) {
                favoriteStar.visibility = View.VISIBLE
            }
            root.setOnLongClickListener {
                onLongClick(film)
                true
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = RcvFilmBinding.inflate(inflater,parent,false)

        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

}