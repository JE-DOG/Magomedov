package ru.je_dog.films.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.je_dog.core.feature.app.ContainerIdProvider
import ru.je_dog.core.feature.presentation.adapter.FilmAdapter
import ru.je_dog.films.di.DaggerFilmsComponent
import ru.je_dog.films.di.FilmsComponent
import ru.je_dog.films.di.dependency.ProductsComponentDepsStore
import ru.je_dog.films.vm.FilmsViewModel
import ru.je_dog.films.vm.ScreenType
import ru.je_dog.products.R
import ru.je_dog.products.databinding.FragmentFilmsBinding
import javax.inject.Inject

class FilmsFragment: Fragment() {

    lateinit var binding: FragmentFilmsBinding
    @Inject
    lateinit var viewModelFactory: FilmsViewModel.Factory
    lateinit var viewModel: FilmsViewModel
    private val adapter by lazy {
        FilmAdapter(
            onLongClick = { film ->
                if (film.isFavorite){
                    viewModel.deleteFromFavorites(film)
                }else {
                    viewModel.saveToFavorites(film)
                }
            },
            onClick = { film ->
                val containerId = (requireActivity() as ContainerIdProvider).containerId
                parentFragmentManager.beginTransaction()
                    .add(containerId,DetailFilmFragment.create(film.filmId),null)
                    .addToBackStack(null)
                    .commit()
            }
        )
    }
    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("ProductFragment"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deps = ProductsComponentDepsStore.deps

        component = DaggerFilmsComponent.factory().create(deps)
        component!!.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[FilmsViewModel::class.java]
        viewModel.getTop100Films()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            rcv.adapter = adapter

            popularScreenBt.setOnClickListener {
                viewModel.updateScreen(ScreenType.Popular)
            }
            favoriteScreenBt.setOnClickListener {
                viewModel.updateScreen(ScreenType.Favorites)
            }

            searchInput.addTextChangedListener {
                val searchText = it.toString()
                viewModel.updateSearchInput(searchText)
            }
            searchBt.setOnClickListener {
                searchInputLayout.visibility = View.VISIBLE
                titleLayout.visibility = View.GONE
            }
            backSearchBt.setOnClickListener {
                searchInputLayout.visibility = View.GONE
                titleLayout.visibility = View.VISIBLE
                viewModel.updateSearchInput("")
            }

            scope.launch {
                launch {
                    viewModel.filteredFilms
                        .collect { products ->
                            adapter.products = products
                        }
                }
                launch {
                    viewModel.showError
                        .collect { errorText ->
                            Log.d("fake",errorText.toString())
                            errorText?.let { Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show() }
                        }
                }
                launch {
                    viewModel.screen
                        .collect { screenType ->
                            when(screenType){

                                ScreenType.Popular -> {
                                    popularScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_selected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primaryTransparent))
                                    }
                                    favoriteScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_unselected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primary))
                                    }
                                }

                                ScreenType.Favorites -> {
                                    favoriteScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_selected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primaryTransparent))
                                    }
                                    popularScreenBt.apply {
                                        background = context.getDrawable(R.drawable.screen_bt_unselected_bg)
                                        setTextColor(context.getColor(ru.je_dog.core.feature.R.color.primary))
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
                                titleLayout.visibility = View.GONE
                                loading.visibility = View.VISIBLE
                            }else {
                                titleLayout.visibility = View.VISIBLE
                                loading.visibility = View.GONE
                            }
                        }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        component = null
    }

    companion object {

        var component: FilmsComponent? = null

    }
}