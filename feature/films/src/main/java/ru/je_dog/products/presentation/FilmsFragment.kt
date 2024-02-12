package ru.je_dog.products.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.presentation.adapter.FilmAdapter
import ru.je_dog.products.R
import ru.je_dog.products.databinding.FragmentProductsBinding
import ru.je_dog.products.di.DaggerFilmsComponent
import ru.je_dog.products.di.FilmsComponent
import ru.je_dog.products.di.dependency.ProductsComponentDepsStore
import ru.je_dog.products.vm.FilmsViewModel
import ru.je_dog.products.vm.ScreenType
import javax.inject.Inject

class FilmsFragment: Fragment() {

    lateinit var binding: FragmentProductsBinding
    @Inject
    lateinit var viewModelFactory: FilmsViewModel.Factory
    lateinit var viewModel: FilmsViewModel
    private val adapter = FilmAdapter(
        onLongClick = {
            if (it.isFavorite){
                viewModel.deleteFromFavorites(it)
            }else {
                viewModel.saveToFavorites(it)
            }
        }
    )
    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("ProductFragment"))
    private val component: FilmsComponent by lazy {
        val deps = ProductsComponentDepsStore.deps
        DaggerFilmsComponent.factory()
            .create(deps)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[FilmsViewModel::class.java]
        viewModel.getTop100Films()
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

            popularScreenBt.setOnClickListener {
                viewModel.updateScreen(ScreenType.Popular)
                viewModel.getTop100Films()
            }
            favoriteScreenBt.setOnClickListener {
                viewModel.updateScreen(ScreenType.Favorites)
            }

            scope.launch {
                launch {
                    viewModel.films
                        .collect { products ->
                            when(viewModel.screen.value){

                                ScreenType.Popular -> {
                                    adapter.products = products
                                }

                                ScreenType.Favorites -> {
                                    adapter.products = viewModel.favoriteFilms.value
                                }
                                else -> {}
                            }
                        }
                }
                launch {
                    viewModel.showError
                        .collect { errorText ->
                            errorText?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                        }
                }
                launch {
                    viewModel.screen
                        .collect { screenType ->
                            when(screenType){

                                ScreenType.Popular -> {
                                    adapter.products = viewModel.films.value
                                    popularScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_selected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primary))
                                    }
                                    favoriteScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_unselected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primaryTransparent))
                                    }
                                }

                                ScreenType.Favorites -> {
                                    adapter.products = viewModel.favoriteFilms.value
                                    favoriteScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_selected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primary))
                                    }
                                    popularScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_unselected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primaryTransparent))
                                    }
                                }
                                else -> {}
                            }
                        }
                }
                launch {
                    viewModel.isLoading
                        .collect {
                            if (it){
                                rcv.visibility = View.GONE
                                loading.visibility = View.VISIBLE
                            }else {
                                rcv.visibility = View.VISIBLE
                                loading.visibility = View.GONE
                            }
                        }
                }
            }
        }
    }
}