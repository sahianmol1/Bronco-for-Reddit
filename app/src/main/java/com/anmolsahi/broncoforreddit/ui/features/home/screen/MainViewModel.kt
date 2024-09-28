package com.anmolsahi.broncoforreddit.ui.features.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _resetScrollHome: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val resetScrollHome: SharedFlow<Boolean>
        get() = _resetScrollHome.asSharedFlow()

    private val _resetScrollSearch: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val resetScrollSearch: SharedFlow<Boolean>
        get() = _resetScrollSearch.asSharedFlow()

    private val _resetScrollSaved: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val resetScrollSaved: SharedFlow<Boolean>
        get() = _resetScrollSaved.asSharedFlow()

    private val _resetScrollAbout: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val resetScrollAbout: SharedFlow<Boolean>
        get() = _resetScrollAbout.asSharedFlow()

    fun resetScrollHome() {
        viewModelScope.launch {
            _resetScrollHome.emit(true)
        }
    }

    fun resetScrollSearch() {
        viewModelScope.launch {
            _resetScrollSearch.emit(true)
        }
    }

    fun resetScrollSaved() {
        viewModelScope.launch {
            _resetScrollSaved.emit(true)
        }
    }

    fun resetScrollAbout() {
        viewModelScope.launch {
            _resetScrollAbout.emit(true)
        }
    }
}
