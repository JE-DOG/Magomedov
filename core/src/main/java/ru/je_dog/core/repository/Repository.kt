package ru.je_dog.core.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import ru.je_dog.core.source.NetworkSource
import ru.je_dog.core.source.StorageSource

abstract class Repository<T>(
    protected val networkSource: NetworkSource<T>,
    protected val storageSource: StorageSource<T>
) {

    open fun getAll(forceWeb: Boolean = false): Flow<List<T>> = flow {
        if (forceWeb){
            saveAndEmit()
        }else {
            val storageItems = storageSource.getAll()
            if (storageItems.isEmpty()){
                saveAndEmit()
            }else {
                emit(storageItems)
            }
        }
    }

    private suspend fun FlowCollector<List<T>>.saveAndEmit() {
        val networkProducts = networkSource.getAll()
        storageSource.saveAll(networkProducts)
        emit(networkProducts)
    }

}