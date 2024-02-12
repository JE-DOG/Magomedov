package ru.je_dog.core.feature.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MainViewModel: ViewModel() {

    val state = MutableStateFlow(MainViewState())

}