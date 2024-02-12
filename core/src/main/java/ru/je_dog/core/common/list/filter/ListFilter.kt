package ru.je_dog.core.common.list.filter

abstract class ListFilter<T> {

    protected abstract var listItemFilters: ListFilterItem<*, T>?

    abstract fun getFilteredList(newList: List<T>): List<T>

}