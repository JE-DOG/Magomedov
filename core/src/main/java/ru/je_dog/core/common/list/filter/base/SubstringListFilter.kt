package ru.je_dog.core.common.list.filter.base

import ru.je_dog.core.common.list.filter.ListFilterItem
import ru.je_dog.core.ext.isSubstringFor

class SubstringListFilter<T>(
    private val getter: (T) -> String
): ListFilterItem<String,T> {

    override var filterValue: String = ""

    override fun execute(item: T): Boolean {
        val fullString = getter(item)
        return if (filterValue.isNotEmpty()){
            filterValue.isSubstringFor(fullString)
        }else {
            true
        }
    }

    override fun updateFilterValue(filterValue: String) {
        this.filterValue = filterValue
    }

}