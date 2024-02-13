package ru.je_dog.core.feature.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import ru.je_dog.core.feature.databinding.RcvFilmBinding
import ru.je_dog.core.feature.model.FilmPresentation
import ru.je_dog.core.feature.presentation.adapter.diff_utill.FilmDiffUtils

class FilmAdapter(
    private val onLongClick: (FilmPresentation) -> Unit,
    private val onClick: (FilmPresentation) -> Unit
): RecyclerView.Adapter<FilmAdapter.ProductHolder>() {

    var products: List<FilmPresentation> = emptyList()
        set(value) {
            val diffUtilCallback = FilmDiffUtils(
                newList = value,
                oldList = field
            )
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    inner class ProductHolder(private val binding: RcvFilmBinding): ViewHolder(binding.root) {
        fun bind(film: FilmPresentation) = with(binding){
            name.text = film.nameRu
            genreAndDate.text = film. genreAndDate
            image.load(film.posterUrlPreview)
            if (film.isFavorite) {
                favoriteStar.visibility = View.VISIBLE
            }else {
                favoriteStar.visibility = View.GONE
            }
            root.apply {
                setOnLongClickListener {
                    onLongClick(film)
                    true
                }
                setOnClickListener {
                    onClick(film)
                }
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