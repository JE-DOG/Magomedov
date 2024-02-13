package ru.je_dog.films.vm

import ru.je_dog.core.common.list.filter.ListFilter
import ru.je_dog.core.common.list.filter.ListFilterItem
import ru.je_dog.core.common.list.filter.base.SubstringListFilter
import ru.je_dog.core.feature.model.FilmPresentation

class FilmsFilter: ListFilter<FilmPresentation>() {

    private val searchListFilterItem = SubstringListFilter<FilmPresentation>(
        getter = { it.nameRu }
    )
    private var filterByFavorite = false

    override var listItemFilters: ListFilterItem<*, FilmPresentation>? = null


    override fun getFilteredList(newList: List<FilmPresentation>): List<FilmPresentation> {
        return newList.filter {  film ->
            if (filterByFavorite){
                if (film.isFavorite){
                    searchListFilterItem.execute(film)
                }else{
                    false
                }
            }else {
                searchListFilterItem.execute(film)
            }

        }
    }

    fun updateSubstringFilterItem(filterValue: String) {
        searchListFilterItem.updateFilterValue(filterValue)
    }

    fun filterByFavorite(filterByFavorite: Boolean){
        this.filterByFavorite = filterByFavorite
    }

}