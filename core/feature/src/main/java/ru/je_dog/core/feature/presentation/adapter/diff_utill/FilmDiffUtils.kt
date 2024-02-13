package ru.je_dog.core.feature.presentation.adapter.diff_utill

import androidx.recyclerview.widget.DiffUtil
import ru.je_dog.core.feature.model.FilmPresentation

class FilmDiffUtils(
    private val oldList: List<FilmPresentation>,
    private val newList: List<FilmPresentation>,
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.nameRu == newItem.nameRu &&
        oldItem.genreAndDate == newItem.genreAndDate &&
        oldItem.posterUrlPreview == newItem.posterUrlPreview &&
        oldItem.isFavorite == newItem.isFavorite
    }
}