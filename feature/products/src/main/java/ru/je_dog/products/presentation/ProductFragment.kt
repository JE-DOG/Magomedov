package ru.je_dog.products.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.presentation.adapter.ProductAdapter
import ru.je_dog.products.databinding.FragmentProductsBinding
import ru.je_dog.products.di.DaggerProductComponent
import ru.je_dog.products.di.ProductComponent
import ru.je_dog.products.di.dependency.ProductsComponentDepsStore
import ru.je_dog.products.vm.ProductViewModel
import javax.inject.Inject

class ProductFragment: Fragment() {

    lateinit var binding: FragmentProductsBinding
    @Inject
    lateinit var viewModelFactory: ProductViewModel.Factory
    lateinit var viewModel: ProductViewModel
    private val adapter = ProductAdapter()
    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("ProductFragment"))
    private val component: ProductComponent by lazy {
        val deps = ProductsComponentDepsStore.deps
        DaggerProductComponent.factory()
            .create(deps)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[ProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            rcv.adapter = adapter

            scope.launch {
                viewModel.products
                    .collect { products ->
                        rcv.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        adapter.products = products
                    }
            }
        }
    }

}