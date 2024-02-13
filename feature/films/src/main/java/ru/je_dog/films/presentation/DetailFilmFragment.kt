package ru.je_dog.films.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.je_dog.films.vm.DetailFilmViewModel
import ru.je_dog.products.databinding.DetailFilmFragmentBinding
import javax.inject.Inject
import kotlin.text.StringBuilder

class DetailFilmFragment: Fragment() {

    lateinit var binding: DetailFilmFragmentBinding
    @Inject
    lateinit var viewModelFactory: DetailFilmViewModel.Factory
    lateinit var viewModel: DetailFilmViewModel
    private val component = FilmsFragment.component
    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("DetailFilmFragment"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component!!.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[DetailFilmViewModel::class.java]
        val filmId = requireArguments().getInt("id",1)
        viewModel.getFilm(filmId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFilmFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        backBt.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        scope.launch {
            with(viewModel){
                launch {
                    showError.collect { erroText ->
                        erroText?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                    }
                }
                launch {
                    isLoading.collect { isLoading ->
                        if (isLoading){
                            loadingLayout.visibility = View.VISIBLE
                            contentLayout.visibility = View.GONE
                        }else {
                            loadingLayout.visibility = View.GONE
                            contentLayout.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    film.collect { film ->
                        if (film != null){
                            posterImage.load(film.coverUrl)
                            nameText.text = film.nameRu
                            descriptionText.text = film.shortDescription
                            val genresTextBuilder = StringBuilder().apply {
                                append("Жанры: ")
                                film.genres.forEachIndexed { index, genre ->
                                    append(genre.genre)
                                    if (index != film.genres.size - 1){
                                        append(",")
                                    }
                                }
                            }
                            genresText.text = genresTextBuilder.toString()
                            val countriesTextBuilder = StringBuilder().apply {
                                append("Страны: ")
                                film.countries.forEachIndexed { index, country ->
                                    append(country.country)
                                    if (index != film.countries.size - 1){
                                        append(",")
                                    }
                                }
                            }
                            countriesText.text = countriesTextBuilder.toString()
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun create(filmId: Int) = DetailFilmFragment().apply {
            val bundle = Bundle().apply {
                putInt("id",filmId)
            }
            arguments = bundle
        }

    }
}