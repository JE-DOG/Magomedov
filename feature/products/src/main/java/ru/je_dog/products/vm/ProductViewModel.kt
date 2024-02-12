package ru.je_dog.products.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.model.ProductPresentation
import ru.je_dog.core.feature.model.toPresentation
import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.repository.Repository
import javax.inject.Inject

class ProductViewModel(
    private val productRepository: Repository<ProductDomain>
): ViewModel() {

    private val _products: MutableStateFlow<List<ProductPresentation>> = MutableStateFlow(emptyList())
    val products: StateFlow<List<ProductPresentation>> = _products

    private val scope = CoroutineScope(Dispatchers.IO + CoroutineName("ProductsViewModel"))

    init {
        getProducts()
    }

    fun getProducts() = scope.launch {
        productRepository.getAll()
            .collect { newProducts ->
                _products.update {
                    newProducts.map { productDomain ->
                        productDomain.toPresentation()
                    }
                }
            }
    }

    class Factory @Inject constructor(
        private val productRepository: Repository<ProductDomain>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductViewModel(productRepository) as T
        }
    }
}